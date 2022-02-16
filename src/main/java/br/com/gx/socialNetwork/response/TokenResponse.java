package br.com.gx.socialNetwork.response;

public class TokenResponse {

	private String type;
	private String token;

	public TokenResponse(String type, String token) {
		this.type = type;
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public String getToken() {
		return token;
	}

}
