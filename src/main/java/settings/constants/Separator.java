package settings.constants;

public enum Separator {

	PIPE("|"), COMMA(","), COLON(":");

	public final String val;

	private Separator(String val) {
		this.val = val;
	}

}
