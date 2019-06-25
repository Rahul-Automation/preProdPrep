package com.qait.sakurai.automation.tests.InstructorFlows.AssignQuestionCollection;

import org.apache.tools.ant.taskdefs.GenerateKey;

public class Demo {

	/*public static void main(String[] args)
    {

        String publicKeyFilename = null;
        String privateKeyFilename = null;

        GenerateKey generateRSAKeys = new GenerateKey();

        if (args.length < 2)
        {
            System.err.println("Usage: java "+ generateRSAKeys.getClass().getName()+
            " Public_Key_Filename Private_Key_Filename");
            System.exit(1);
        }

        publicKeyFilename = args[0].trim();
        privateKeyFilename = args[1].trim();
        generateRSAKeys.generate(publicKeyFilename, privateKeyFilename);

    }
	
	private void generate (String publicKeyFilename, String privateFilename){

        try {

            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            // Create the public and private keys
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
            BASE64Encoder b64 = new BASE64Encoder();

            SecureRandom random = createFixedRandom();
            generator.initialize(1024, random);

            KeyPair pair = generator.generateKeyPair();
            Key pubKey = pair.getPublic();
            Key privKey = pair.getPrivate();

            System.out.println("publicKey : " + b64.encode(pubKey.getEncoded()));
            System.out.println("privateKey : " + b64.encode(privKey.getEncoded()));

            BufferedWriter out = new BufferedWriter(new FileWriter(publicKeyFilename));
            out.write(b64.encode(pubKey.getEncoded()));
            out.close();

            out = new BufferedWriter(new FileWriter(privateFilename));
            out.write(b64.encode(privKey.getEncoded()));
            out.close();


        }
        catch (Exception e) {
            System.out.println(e);
        }
    }*/

}
