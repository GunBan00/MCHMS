package com.jeonbuk.mchms;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MchmsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MchmsApplication.class, args);
	}


	@Override
	public void run(String... args)  {

		/*
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword("mchms8745");  // <- Fill Your Password

        String enc = pbeEnc.encrypt("ch3cooh");
        System.out.println("enc = " + enc);

        String decrypt = pbeEnc.decrypt("P89rWWKmAoSN6UOFtOmrlg==");
        System.out.println(decrypt);
        */

	}
}
