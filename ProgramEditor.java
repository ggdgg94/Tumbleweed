import javafx.scene.web.HTMLEditor;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class ProgramEditor extends Application
{
	final static public String operators[] = {"+", "-", "*", "/", "|", "&", ">", "<", "="};
	final static public String keywords[] = {"if", "else", "while", "for"};
	final static public String endCh[] = {" ", "\t", "\r", "(", "{", "[", ")", "}", "]", ";", ":",",",".","\""};


	final static public String htmlHead = "<html dir=\"ltr\"><head></head><body contenteditable=\"true\">";
	final static public String htmlEnd = "</body></html>";

	private Word word;
	private List<Word> words;
	private String bodyText;
	private String ch;
	
	private void commitWord()
	{
		this.bodyText += this.word.getHtmlText();
		this.words.add(word);
		this.word = new Word();
	}

	private boolean isEnd(String ch)
	{
		return Arrays.asList(endCh).contains(ch);
	}
	private boolean isOperator(String ch)
	{
		return Arrays.asList(operators).contains(ch);
	}

	public void start(Stage root)
	{
		HTMLEditor editor = new HTMLEditor();
		this.words = new ArrayList<Word>();
		this.word = new Word();
		this.bodyText = "";

		Node nodes[] = editor.lookupAll(".tool-bar").toArray(new Node[0]);
		for(Node node : nodes){
			node.setVisible(false);
			node.setManaged(false);
		}
		editor.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			this.ch = e.getText();
			if(!this.ch.equals("\b")){
				if(isEnd(this.ch) || isOperator(this.ch)){
					if(this.word.getLength() == 0){
						this.word.addCh(this.ch);
						commitWord();
					}
					if(this.word.getLength() > 0){
						commitWord();
						this.word.addCh(this.ch);
						commitWord();
					}
				}else{
					if(e.getCode().isLetterKey() || e.getCode().isDigitKey()){
						this.word.addCh(this.ch);
					}
				}
			}else{
				if(this.word.getLength() > 0){
					this.word.removeCh();
				}else if(this.word.getLength() == 0 && this.words.size() > 0){
					this.word = words.get(this.words.size()-1);
					words.remove(words.size()-1);
					this.bodyText = "";
					for(Word w : words){
						bodyText += w.getHtmlText();
					}
					this.word.removeCh();
				}else{

				}


			}

			//this happens last
			editor.setHtmlText(this.htmlHead + bodyText + this.word.getHtmlText() + this.htmlHead);

		});

		root.setTitle("Test1");
		Scene scene = new Scene(new GridPane(), 800, 600);
		((GridPane) scene.getRoot()).getChildren().addAll(editor);
		root.setScene(scene);
		root.show();
	}

	public static void main(String args[])
	{
		launch(args);
	}

}
