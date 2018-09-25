
package com.xdaocloud.futurechain.ethereum;

import com.alibaba.fastjson.JSON;
import com.xdaocloud.futurechain.constant.AESConstant;
import com.xdaocloud.futurechain.dto.req.ethereum.CreateWalletRequest;
import com.xdaocloud.futurechain.mapper.EthereumWalletMapper;
import com.xdaocloud.futurechain.model.EthereumWallet;
import com.xdaocloud.futurechain.util.EncoderUtils;
import com.xdaocloud.futurechain.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.UUID;


/**
 * 以太坊账户相关操作类
 */
@Service
public class EthereumAccount {

    private static Logger LOGGER = LoggerFactory.getLogger(EthereumAccount.class);

    @Autowired
    private Web3j web3j;

    @Autowired
    private Admin admin;

    @Value("${file.path}")
    private String filePath;

    @Autowired
    private EthereumWalletMapper ethereumWalletMapper;



/**
     * 创建钱包
     *
     * @param request
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidAlgorithmParameterException
     * @throws CipherException
     * @throws IOException
     */

    public EthereumWallet createEthereumWallet( CreateWalletRequest request) throws Exception {
       return onlineSignature(request);
    }


/**
     * 创建在线钱包
     * @param request
     * @return
     * @throws IOException
     */

    private EthereumWallet onlineSignature(CreateWalletRequest request) throws Exception {

        NewAccountIdentifier accountIdentifier = admin.personalNewAccount(request.getPassword()).send();
        LOGGER.info("==onlineSignature==" + JSON.toJSONString(accountIdentifier));
        //加密钱包密码
        String walletPwd = EncoderUtils.encryptA(AESConstant.KEY, request.getPassword());
        EthereumWallet ethereumWallet = new EthereumWallet();
        ethereumWallet.setWalletAddress(accountIdentifier.getAccountId());
        ethereumWallet.setPassPhrase(walletPwd);
        ethereumWallet.setUserId(Long.valueOf(request.getUserid()));
        //保存钱包数据
        int i = ethereumWalletMapper.insertSelective(ethereumWallet);
        ethereumWallet.setPassPhrase(request.getPassword());
        return ethereumWallet;
    }


/**
     * 创建离线钱包
     *
     * @param userid   用户id
     * @param password 钱包密码
     * @return
     */

    private EthereumWallet offlineSignature(Long userid, String password) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //路径拼接，生成唯一路径
        String path = filePath + uuid + "/";
        //判断文件夹是否存在，不存在就创建文件夹
        if (!FileUtils.existsDirectory(path)) {
            FileUtils.forceDirectory(path);
        }
        // 生成离线钱包
        String fileName = WalletUtils.generateNewWalletFile(password, new File(path), false);
        Credentials ALICE = WalletUtils.loadCredentials(password, path + fileName);
        String privateKey = EncoderUtils.encryptA(AESConstant.KEY, ALICE.getEcKeyPair().getPrivateKey().toString());
        //加密钱包文件密码
        String walletPwd = EncoderUtils.encryptA(AESConstant.KEY, password);
        EthereumWallet ethereumWallet = new EthereumWallet(userid, ALICE.getAddress(), privateKey, ALICE.getEcKeyPair().getPublicKey().toString(), path + fileName, walletPwd, false);
        //保存钱包数据
        int i = ethereumWalletMapper.insertSelective(ethereumWallet);
        if (i > 0) {
            ethereumWallet.setPassPhrase(password);
            ethereumWallet.setWalletPrivateKey(ALICE.getEcKeyPair().getPrivateKey().toString());
            return ethereumWallet;
        } else {
            return null;
        }
    }

}

