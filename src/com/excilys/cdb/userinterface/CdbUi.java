package com.excilys.cdb.userinterface;

import java.io.*;
import java.util.Scanner;

import com.excilys.cdb.controller.CdbController;

public class CdbUi {
	private InputStream in;
	private PrintStream out;
	private PrintStream err;
	private Scanner sc;
	private String cmd;
	
	public CdbUi (InputStream inStream, PrintStream outStream) {
		this(inStream,outStream,outStream);
	}
	
	public CdbUi(InputStream inStream, PrintStream outStream, PrintStream errStream) {
		this.in = inStream;
		this.out = outStream;
		this.err = errStream;
		this.sc = new Scanner(this.in);
		this.cmd = "";
	}
	
	public void run() {
		// Welcome
		this.println(this.out,"Welcome\n(Try: help / quit)");
		while (true) {
			this.cmd = sc.nextLine();
			switch (this.cmd) {
				case "stop":
				case "exit":
					this.println(this.out,"Bye !");
					return;
				default:
					try {
						this.println(this.out,CdbController.getInstance().treatMessage(cmd));
					} catch (Exception e) {
						this.println(this.err,e.getMessage());
					}
			}
			this.out.println();
		}
	}
	
	private void println(PrintStream p, String msg) {
		p.println(msg);
	}
}
