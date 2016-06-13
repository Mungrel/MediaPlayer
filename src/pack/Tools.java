package pack;

import java.io.File;

import javax.swing.JFileChooser;

public class Tools {
	
	private static File lastDir = null;
	
	public static File openFile(){
		JFileChooser jfc = new JFileChooser();
		
		if (lastDir != null){
			jfc.setCurrentDirectory(lastDir);
		}
		
		jfc.showOpenDialog(MainFrame.mFrame);
		File chosenFile = jfc.getSelectedFile();
		
		if (chosenFile != null){
		lastDir = chosenFile.getParentFile();
		}
		
		return chosenFile;
	}
	
	public static void toggleButtons(boolean visible){
		MainFrame.mFrame.btnPlay.setVisible(visible);
		MainFrame.mFrame.btnOpen.setVisible(visible);
		MainFrame.mFrame.btnHide.setVisible(visible);
		MainFrame.mFrame.btnStop.setVisible(visible);
		MainFrame.mFrame.btnMute.setVisible(visible);
		MainFrame.mFrame.btnShow.setVisible(!visible);
	}
	
}
