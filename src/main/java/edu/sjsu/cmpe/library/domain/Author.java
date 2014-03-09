package edu.sjsu.cmpe.library.domain;

import java.util.Random;

public class Author {
private int id;
private String name;

public Author()
{
	Random random = new Random();
	this.id = random.nextInt(100);
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

}
