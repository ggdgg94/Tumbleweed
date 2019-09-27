import java.util.Arrays;
public class Word
{
	private String text;
	private boolean isOperator;
	private boolean isString;
	private boolean isKeyword;
	private boolean isSpecial;
	private boolean keyPossible;

	public Word()
	{
		this.text = "";
		this.isOperator = false;
		this.isString = false;
		this.isKeyword = false;
		this.keyPossible = true;
	}
	public int getLength()
	{
		return this.text.length();
	}
	public String getHtmlText()
	{
		if(isOperator)
			return "<span style=\"color: rgb(255, 0, 0);\">" + this.text + "</span>";

		if(isString)
			return "";

		if(isSpecial){
			if(this.text.equals("\t"))
				return "&emsp;";
			else
				return "<br>";
		}
			

		if(isKeyword)
			return "<span style=\"color: rgb(0, 0, 255);\">" + this.text + "</span>";

		return this.text;

	}

	public String getRawText()
	{
		return this.text;
	}
	private boolean inSpecial(String s)
	{
		String special[] = {"\t", "\r"};
		return Arrays.asList(special).contains(s);
	}
	private boolean inOperator(String s)
	{
		String operators[] = {"+", "-", "*", "/", "|", "&", ">", "<", "="};
		return Arrays.asList(operators).contains(s);
	}
	private boolean inKeywords(String s)
	{
		String keywords[] = {"if", "else", "while", "for"};
		return Arrays.asList(keywords).contains(s);
	}
	private boolean inEnding(char s)
	{
		char endCh[] = {' ', '\r', '(', '{', '[', ')', '}', ']'};
		return Arrays.asList(endCh).contains(s);
	}
	public void addCh(String ch)
	{
		if(inOperator(ch) && !this.isString){
			this.isOperator = true;
			this.keyPossible = false;
		}
		if(inSpecial(ch)){
			this.isSpecial = true;
		}

		/*
		if(ch.equals("\"") && !this.isString){
			this.isString = true;
			this.keyPossible = false;
		}
		*/

		this.text += ch;
		if(keyPossible){
			char first = this.text.charAt(0);
			int len = this.text.length();

			if( (first == 'i' && len < 3) ||
				(first == 'f' && len < 4) ||
				(first == 'e' && len < 5) ||
				(first == 'w' && len < 6)){
				if(inKeywords(this.text)){
					this.isKeyword = true;
				}
			}else{
				this.keyPossible = false;
				this.isKeyword = false;
			}
		}
	}
	public void removeCh()
	{
		System.out.println("Removing: " + this.text.charAt(this.text.length()-1));
		if(this.text.length() == 1){
			this.text = "";
			this.isOperator = false;
			this.isString = false;
			this.isKeyword = false;
			this.keyPossible = true;
		}else{
			this.text = this.text.substring(0,this.text.length()-1);
			this.isOperator = false;
			this.isString = false;
			this.isKeyword = false;
			this.keyPossible = true;
			/*
			if(inOperator(this.text) && !this.isString){
				this.isOperator = true;
				this.keyPossible = false;
			}
			if(inSpecial(this.text)){
				this.isSpecial = true;
			}
			*/
			if(keyPossible){
				char first = this.text.charAt(0);
				int len = this.text.length();

				if( (first == 'i' && len < 3) ||
					(first == 'f' && len < 4) ||
					(first == 'e' && len < 5) ||
					(first == 'w' && len < 6)){
					if(inKeywords(this.text)){
						this.isKeyword = true;
					}
				}else{
					this.keyPossible = false;
					this.isKeyword = false;
				}
			}

		}


	}

}

