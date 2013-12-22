package com.araujo.jacket.message;

import com.araujo.jacket.factory.JacketFactory;

public class Printer {
	public static void print(Message message, Severity severity) {
		if (severity.equals(Severity.OUT)) {
			System.out.println(message.toString());
		} else if (severity.equals(Severity.ERR)) {
			System.err.println(message.toString());
		}
	}

	public static void print(Severity severity, String message,
			String... params) {
		Message.Builder builder = JacketFactory
				.getMessageBuilder(message);
		for (String string : params) {
			builder.param(string);
		}
		Printer.print(builder.build(), severity);
	}
}
