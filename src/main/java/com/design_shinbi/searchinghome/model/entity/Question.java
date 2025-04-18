package com.design_shinbi.searchinghome.model.entity;

public class Question {
	private int id;
	private String questionText;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private int correctChoice;
	private String explanation;
	private String imageUrl;
	
	public Question() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getQuestionText() {
		return questionText;
	}
	
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
	public String getChoice1() {
		return choice1;
	}
	
	public void setChoice1(String choice1) {
		this.choice1 = choice1;
	}
	
	public String getChoice2() {
		return choice2;
	}
	
	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}
	
	public String getChoice3() {
		return choice3;
	}
	
	public void setChoice3(String choice3) {
		this.choice3 = choice3;
	}
	
	public String getChoice4() {
		return choice4;
	}
	
	public void setChoice4(String choice4) {
		this.choice4 = choice4;
	}
	
	public int getCorrectChoice() {
		return correctChoice;
	}
	
	public void setCorrectChoice(int correctChoice) {
		this.correctChoice = correctChoice;
	}
	
	public String getExplanation() {
		return explanation;
	}
	
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Override
	public String toString() {
	    return "Question {" +
	           "id=" + id +
	           ", questionText='" + questionText + '\'' +
	           ", choice1='" + choice1 + '\'' +
	           ", choice2='" + choice2 + '\'' +
	           ", choice3='" + choice3 + '\'' +
	           ", choice4='" + choice4 + '\'' +
	           ", correctChoice=" + correctChoice +
	           ", explanation='" + explanation + '\'' +
	           ", imageUrl='" + imageUrl + '\'' +
	           '}';
	}


}
