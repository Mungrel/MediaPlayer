package pack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.Color;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	private JPanel contentPane;
	public static MainFrame mFrame;
	private final EmbeddedMediaPlayerComponent player = new EmbeddedMediaPlayerComponent();
	protected final EmbeddedMediaPlayer video = player.getMediaPlayer();
	private static boolean isPlayBtn = true;
	protected JButton btnPlay;
	protected JButton btnStop;
	protected JButton btnHide;
	private JPanel bottomRowButtons;
	protected JButton btnOpen;
	protected JButton btnShow;
	protected JButton btnMute;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new NativeDiscovery().discover();
					mFrame = new MainFrame();
					mFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Media Player");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);		
		
		bottomRowButtons = new JPanel();
		bottomRowButtons.setBackground(Color.BLACK);
		bottomRowButtons.addMouseListener(new MouseHandler());
		
		contentPane.add(bottomRowButtons, BorderLayout.SOUTH);
		
		btnPlay = new JButton("Play");
		btnPlay.setBackground(Color.WHITE);
		btnPlay.setEnabled(false);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isPlayBtn){
					//was a play button when clicked, do play stuff
				video.play();
				btnPlay.setText("Pause");
				isPlayBtn = false;
				} else {
					//button was a pause button, do pause stuff
					video.pause();
					btnPlay.setText("Play");
					isPlayBtn = true;
				}
			}
		});
		bottomRowButtons.add(btnPlay);
		
		btnStop = new JButton("Stop");
		btnStop.setBackground(Color.WHITE);
		btnStop.setEnabled(false);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (video.isPlaying()){
					btnPlay.doClick();
				}
				video.stop();
			}
		});
		bottomRowButtons.add(btnStop);
		
		btnOpen = new JButton("Open");
		btnOpen.setBackground(Color.WHITE);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (video.isPlaying()){
				btnPlay.doClick();
				}
				File chosenFile = Tools.openFile();
				if (chosenFile == null){
					return;
				} else {
					video.prepareMedia(chosenFile.getAbsolutePath());
						btnPlay.setEnabled(true);
						btnStop.setEnabled(true);
					btnPlay.doClick();
					mFrame.setTitle(getTitle()+" - "+chosenFile.getName());
				}
			}
		});
		bottomRowButtons.add(btnOpen);
		
		btnHide = new JButton("Hide");
		btnHide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tools.toggleButtons(false);
				
			}
		});
		btnHide.setBackground(Color.WHITE);
		bottomRowButtons.add(btnHide);
		
		btnShow = new JButton("");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tools.toggleButtons(true);
			}
		});
		btnShow.setBorderPainted(false);
		ImageIcon myIcon = new ImageIcon((MainFrame.class.getResource("/show.png")));
		Image img = myIcon.getImage();
		Image newimg = img.getScaledInstance(14, 15,  java.awt.Image.SCALE_SMOOTH);
		myIcon = new ImageIcon(newimg);
		btnShow.setIcon(myIcon);
		btnShow.setPreferredSize(new Dimension(14, 15));
		btnShow.setVisible(false);
		
		btnMute = new JButton("Mute");
		btnMute.setBackground(Color.WHITE);
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				video.mute();
				if (video.isMute()){
					btnMute.setText("Unmute");
				} else {
					btnMute.setText("Mute");
				}
			}
		});
		bottomRowButtons.add(btnMute);
		bottomRowButtons.add(btnShow);
	
		contentPane.add(player, BorderLayout.CENTER);
	}
}
