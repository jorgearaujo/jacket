package com.araujo.jacket.message;

import java.util.ArrayList;
import java.util.List;

public class Message {
	private String message;

	public Message(Message.Builder builder) {
		int i = 0;
		String newMessage = builder.base;
		while (newMessage.indexOf("{%}") != -1) {
			newMessage = builder.base.replaceFirst("\\{%\\}",
					builder.params.get(i));
			i++;
		}
		message = newMessage;
	}

	@Override
	public String toString() {
		return message.toString();
	}

	public static class Builder {

		private String base;
		private List<String> params;

		public Builder(String base) {
			this.base = base;
			params = new ArrayList<String>();
		}

		public Builder param(String param) {
			params.add(param);
			return this;
		}

		public Message build() {
			return new Message(this);
		}

	}
}
