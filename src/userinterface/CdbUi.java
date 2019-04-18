package userinterface;

import java.io.*;
import java.util.Scanner;

import com.excilys.cdb.controller.CdbController;

public class CdbUi {
	private InputStream inputStream;
	private PrintStream outputStream;
	private PrintStream errorStream;
	private Scanner sc;
	private String cmd;
	
	public CdbUi (InputStream inStream, PrintStream outStream) {
		this(inStream,outStream,outStream);
	}
	
	public CdbUi(InputStream inStream, PrintStream outStream, PrintStream errStream) {
		this.inputStream = inStream;
		this.outputStream = outStream;
		this.errorStream = errStream;
		this.sc = new Scanner(this.inputStream);
		this.cmd = "";
	}
	
	public void run() {
		while (true) {
			this.cmd = sc.nextLine();
			switch (this.cmd) {
				case "exit":
					this.println(this.outputStream,"Bye !");
					return;
				default:
					try {
						this.println(this.outputStream,CdbController.getInstance().treatMessage(cmd));
					} catch (Exception e) {
						this.println(this.errorStream,e.getMessage());
					}
			}
			this.outputStream.println();
		}
	}
	
	private void println(PrintStream p, String msg) {
		p.println(msg);
	}
}
