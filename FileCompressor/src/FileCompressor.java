import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class FileCompressor {

	private JFrame frmKzipFileCompressor;
	private JTextField txtEnterFileName;
	private JProgressBar progressBar;
	private Task task;
	File selectedFile;
	boolean fileSelected = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileCompressor window = new FileCompressor();
					window.frmKzipFileCompressor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FileCompressor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmKzipFileCompressor = new JFrame();
		frmKzipFileCompressor.setTitle("KZip File Compressor");
		frmKzipFileCompressor.setBounds(100, 100, 700, 600);
		frmKzipFileCompressor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKzipFileCompressor.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmKzipFileCompressor.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                //
                if(!fileSelected)
                	JOptionPane.showMessageDialog(frmKzipFileCompressor,"No File Chosen!");
                else{
                	System.out.println(selectedFile.getAbsolutePath());
                	System.exit(0);
                }
                	
            }
        } );
		frmKzipFileCompressor.getContentPane().setLayout(null);
		
		URL url = FileCompressor.class.getResource("/resources/Compress_PDF.png");
		JLabel lblNewLabel = new JLabel(new ImageIcon(url));
		lblNewLabel.setBounds(26, 51, 406, 342);
		frmKzipFileCompressor.getContentPane().add(lblNewLabel);
		
		JButton btnSelectFile = new JButton("Choose File..");
		addActionListenerSelect(btnSelectFile);
		btnSelectFile.setBounds(433, 437, 155, 36);
		frmKzipFileCompressor.getContentPane().add(btnSelectFile);
		
		txtEnterFileName = new JTextField();
		txtEnterFileName.setText("Enter file name...");
		txtEnterFileName.setBounds(26, 439, 348, 33);
		frmKzipFileCompressor.getContentPane().add(txtEnterFileName);
		txtEnterFileName.setColumns(10);
		
		JButton btnStart = new JButton("Start Compression!");
		addActionListenerStart(btnStart);
		btnStart.setBounds(472, 201, 171, 25);
		frmKzipFileCompressor.getContentPane().add(btnStart);
		
		progressBar = new JProgressBar();
		//progressBar.setValue(25);
	    progressBar.setStringPainted(true);
	    Border border = BorderFactory.createTitledBorder("Compressing...");
	    progressBar.setBorder(border);
		progressBar.setBounds(26, 485, 617, 36);
		frmKzipFileCompressor.getContentPane().add(progressBar);
		
	}
	
	void addActionListenerSelect(JButton button){
		  button.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ae) {
		        JFileChooser fileChooser = new JFileChooser();
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		          selectedFile = fileChooser.getSelectedFile();
		          txtEnterFileName.setText(selectedFile.getAbsolutePath());
		          fileSelected = true;
		          //
		        }
		      }
		    });
	  }
	
	void addActionListenerStart(JButton button){
		  button.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ae) {
		    	  if(txtEnterFileName.getText() == null || txtEnterFileName.getText().isEmpty() || txtEnterFileName.getText().equals("Enter file name...")){
		    		  JOptionPane.showMessageDialog(frmKzipFileCompressor,"No File Chosen!");
		    	  }else{
		    	  task = new Task();                
		           task.start();
		        compressFile(txtEnterFileName.getText());
		    	  }
		      }
		    });
	  }
	
	void compressFile(String path){
		byte[] buffer = new byte[1024];

    	try{
    		FileOutputStream fos = new FileOutputStream(".//CompressedFile.zip");
    		ZipOutputStream zos = new ZipOutputStream(fos);
    		ZipEntry ze= new ZipEntry(path.substring(path.lastIndexOf("/") + 1));
    		zos.putNextEntry(ze);
    		FileInputStream in = new FileInputStream(path);
    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}
    		in.close();
    		zos.closeEntry();
    		zos.close();

    	}catch(IOException ex){
    	   ex.printStackTrace();
    	}
	}
	
	private class Task extends Thread {    
	      public Task(){
	      }

	      public void run(){
	         for(int i =0; i<= 100; i+=10){
	            final int progress = i;
	            SwingUtilities.invokeLater(new Runnable() {
	               public void run() {
	                  progressBar.setValue(progress);
	               }
	            });
	            try {
	               Thread.sleep(100);
	            } catch (InterruptedException e) {}
	         }
	      }
	   }
	
	
}
