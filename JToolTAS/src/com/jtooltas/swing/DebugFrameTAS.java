/**
 * 
 */
package com.jtooltas.swing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jtooltas.recorder.TASPlayer;
import com.jtooltas.recorder.TASRecorder;

/**
 *
 * @date 4 oct. 2013
 * @author Eptwalabha
 */
public class DebugFrameTAS extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5201470284977866099L;

	private JButton button_record;
	private JButton button_stop;
	private JButton button_play;
	
	private TASRecorder recorder;
	private TASPlayer player;
	private Robot robot;
	
	public DebugFrameTAS(TASRecorder recorder, TASPlayer player, Robot robot) {
		
		this.setTitle("TAS");
		this.setLocationRelativeTo(null);
		this.setSize(new Dimension(200, 200));

		this.recorder = recorder;
		this.player = player;
		this.robot = robot;
		
		this.getContentPane().add(this.getMainPanel());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private JPanel getMainPanel() {
		
		this.button_record = new JButton("record");
		this.button_stop = new JButton("stop");
		this.button_play = new JButton("player");		
		
		FlowLayout layout = new FlowLayout();
		JPanel panel = new JPanel(layout);
		panel.add(this.button_record);
		panel.add(this.button_stop);
		panel.add(this.button_play);
		
		this.button_record.addActionListener(this);
		this.button_stop.addActionListener(this);
		this.button_play.addActionListener(this);
		
		return panel;
	}

	/**
	 * Start recording
	 */
	private void launchRecorder() {

		System.out.print("launching recording...");
		if (!this.recorder.isRecording()) {
			Thread t = new Thread(this.recorder);
			t.start();
		}
	}
	
	/**
	 * Stop recording
	 */
	private void stopRecorder() {
	
		System.out.print("stopping recording...");
		this.recorder.stopRecording();
		this.player.stopPlaying();
		System.out.println("done!");
	}
	
	/**
	 * Stop the recording, start the playing
	 */
	private void launchPlayer() {
		
		System.out.print("launching playing...");
		if (!this.player.isPlaying()) {
			
			if (!this.recorder.isRecording()) {
				this.recorder.stopRecording();
			}
			
			this.player.setActions(this.recorder.getActionTASListFromRecord(this.robot));
			
			try {
				this.player.play();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == this.button_record) this.launchRecorder();
		if (e.getSource() == this.button_stop) this.stopRecorder();
		if (e.getSource() == this.button_play) this.launchPlayer();
	}
}
