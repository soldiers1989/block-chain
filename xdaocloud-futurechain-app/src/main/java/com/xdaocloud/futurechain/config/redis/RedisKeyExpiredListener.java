package com.xdaocloud.futurechain.config.redis;

import static com.xdaocloud.futurechain.constant.Constant.REDIS_ENVELOP;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.xdaocloud.futurechain.eos.EosAccount;
import com.xdaocloud.futurechain.mapper.EosWalletMapper;
import com.xdaocloud.futurechain.mapper.GrabOreEnvelopeMapper;
import com.xdaocloud.futurechain.mapper.OreEnvelopeMapper;
import com.xdaocloud.futurechain.model.OreEnvelope;


public class RedisKeyExpiredListener {
    private static Logger LOG = LoggerFactory.getLogger(RedisKeyExpiredListener.class);

    private CountDownLatch latch;

    @Autowired
    private OreEnvelopeMapper oreEnvelopeMapper;

    @Autowired
    private GrabOreEnvelopeMapper grabOreEnvelopeMapper;

    @Autowired
    private EosAccount eosAccount;

    @Autowired
    private EosWalletMapper eosWalletMapper;

    public RedisKeyExpiredListener(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiveMessage(String message) throws Exception {
        LOG.info(new Date().toString() + "::" + "redis--key::" + message);
        latch.countDown();
        if (message.contains(REDIS_ENVELOP)) {
            Long envelopeId = Long.valueOf(message.replace(REDIS_ENVELOP, ""));
            OreEnvelope oreEnvelope = oreEnvelopeMapper.selectByPrimaryKey(envelopeId);
            if (oreEnvelope != null) {
                if (oreEnvelope.getResidue() != 0) {
                    //查询已经抢矿包总和
                    BigDecimal sum = grabOreEnvelopeMapper.findSumByOreEnvelopeId(envelopeId);
                    if (sum == null) {
                        sum = new BigDecimal(0);
                    }
                    //剩余麦钻返回给用户
                    BigDecimal surplus = oreEnvelope.getAmount().subtract(sum).setScale(4, BigDecimal.ROUND_DOWN);
                    if (surplus.compareTo(new BigDecimal(0)) > 0) {
                        String wallet = eosWalletMapper.findWalletNameByUserId(oreEnvelope.getUserId());
                        //发起eos交易
                        eosAccount.fromTempTransaction(wallet, surplus.toString(), "矿包退还");
                        oreEnvelopeMapper.updateOreStateFalseByPrimaryKey(envelopeId);
                        LOG.info("==矿包退还成功==");
                    }

                }
            }
        }
    }

}
