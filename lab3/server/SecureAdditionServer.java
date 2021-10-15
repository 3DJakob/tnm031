
// An example class that uses the secure server socket class

import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.security.*;
import java.util.StringTokenizer;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SecureAdditionServer {
	private int port;
	// This is not a reserved port number
	static final int DEFAULT_PORT = 8189;
	static final String KEYSTORE = "/Users/jakob/coding/tnm031/lab3/server/LIUkeystore.ks";
	static final String TRUSTSTORE = "/Users/jakob/coding/tnm031/lab3/server/LIUtruststore.ks";
	static final String KEYSTOREPASS = "123456";
	static final String TRUSTSTOREPASS = "abcdef";

	/**
	 * Constructor
	 * 
	 * @param port The port where the server will listen for requests
	 */
	SecureAdditionServer(int port) {
		this.port = port;
	}

	/** The method that does the work for the class */
	public void run() {
		try {
			//Create Keystore and truststore
			KeyStore ks = KeyStore.getInstance("JCEKS");
			ks.load(new FileInputStream(KEYSTORE), KEYSTOREPASS.toCharArray());

			KeyStore ts = KeyStore.getInstance("JCEKS");
			ts.load(new FileInputStream(TRUSTSTORE), TRUSTSTOREPASS.toCharArray());

			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, KEYSTOREPASS.toCharArray());

			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(ts);

			// Use the Transport Layer Security standard.
			SSLContext sslContext = SSLContext.getInstance("TLS");
			// Default random number seed will be used if NULL
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			SSLServerSocketFactory sslServerFactory = sslContext.getServerSocketFactory();
			// Listen to the specified port
			SSLServerSocket sss = (SSLServerSocket) sslServerFactory.createServerSocket(port);
			System.out.println(sss.getSupportedCipherSuites()[0].toString());
			sss.setEnabledCipherSuites(sss.getSupportedCipherSuites());
			System.out.println("\n>>>> SecureAdditionServer: active");
			System.out.println("\n>>>> Done ");
			// Listening for connections
			SSLSocket incoming = (SSLSocket) sss.accept();

			BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
			PrintWriter out = new PrintWriter(incoming.getOutputStream(), true);

			String str;
			while (!(str = in.readLine()).equals("")) {
				String operation = "";
				String fileName = "";
				String content = "";
				String message = "";
				StringTokenizer st = new StringTokenizer(str);
				try {
					while (st.hasMoreTokens()) {
						if (operation == "") {
							operation = st.nextToken();
						} else if (fileName == "") {
							fileName = st.nextToken();
						} else {

							content = content + st.nextToken() + ' ';
						}
					}

					// Execute

					switch (operation) {
						case "delete":
							File fileToDelete = new File(fileName + ".txt");
							if (fileToDelete.delete()) {
								System.out.println("Deleted the file: " + fileToDelete.getName());
								message = "Deleted the file: " + fileToDelete.getName();
							} else {
								System.out.println("Failed to delete the file.");
								message = "Failed to delete the file.";
							}
							break;
						case "upload":
							try {
								File myObj = new File(fileName + ".txt");
								if (myObj.createNewFile()) {

									System.out.println("File created: " + myObj.getName());
									message = "File created: " + myObj.getName();

									try (FileWriter f = new FileWriter(myObj.getName(), true);
											BufferedWriter b = new BufferedWriter(f);
											PrintWriter p = new PrintWriter(b);) {
										p.println(content);
									} catch (IOException i) {
										i.printStackTrace();
									}

								} else {
									System.out.println("File already exists.");
									message = "File already exists.";
								}

							} catch (IOException e) {
								System.out.println("An error has occurred.");
								message = "An error has occurred.";
								e.printStackTrace();
							}
							break;
						case "download":
							try (BufferedReader br2 = new BufferedReader(new FileReader(fileName + ".txt"))) {
								String line;
								while ((line = br2.readLine()) != null) {
									// System.out.println(line);
									message = message + line;
								}
								
							} catch (IOException e) {
								message = "Error no such file";
							}
							break;
						default:
							System.out.println("Not valid");
							message = "Invalid command";
							break;
					}

					out.println(message);
				} catch (Exception e) {
					out.println("An unexpected error occured");
				}
			}

			incoming.close();
		} catch (Exception x) {
			System.out.println(x);
			x.printStackTrace();
		}
	}

	/**
	 * The test method for the class
	 * 
	 * @param args[0] Optional port number in place of the default
	 */
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		SecureAdditionServer addServe = new SecureAdditionServer(port);

		while (true) {
			addServe.run();
		}
	}
}
