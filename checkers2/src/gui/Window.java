package gui;

import main.Engine;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JFrame;


public class Window extends JFrame{



	public Window(int width, int height, String title, Engine engine) {
		pack();
		setSize(width + getInsets().left + getInsets().right, height + getInsets().top + getInsets().bottom);

		setTitle(title);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		//add game element
		add(engine);
		engine.start();
	}
}
