import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class TrieNode
{
	TrieNode[] arr;
	boolean isEnd;
	
	public TrieNode()
	{
		isEnd=false;
		arr=new TrieNode[26];
		for(int i=0;i<26;i++)
		{
			arr[i]=null;
		}
	}
}

class Trie
{
	private TrieNode root;
	private int letter_count,word_count;
	private char[] letters;
	String[] words_selected;
	
	public Trie() throws IOException
	{
		root=new TrieNode();
		letter_count=0;
		word_count=0;
		words_selected=new String[109576];
		letters=new char[100];
		try{
			InputStream fis=new FileInputStream("D:/govind/JAVA PRGMS/031/dict.txt");
			InputStreamReader isr=new InputStreamReader(fis);
			BufferedReader br=new BufferedReader(isr);
			String line;
			while( (line = br.readLine())!= null)
			{
				insert(line);
			}
		}
		catch(IOException e)
		{
			
		}
		
	}
	public void refresh()
	{
		letter_count=0;
		word_count=0;
		
		Arrays.fill(letters, '\0');
		Arrays.fill(words_selected, null);
		
	}
	
	private void insert(String word)
	{
		TrieNode temp=root;
		for(int i=0;i<word.length();i++)
		{
			char letter=word.charAt(i);
			int index=letter-'a';
			if(temp.arr[index] == null)
			{
				TrieNode current=new TrieNode();
				temp.arr[index]=current;
				temp=current;
			}
			else
			{
				temp=temp.arr[index];
			}
		}
		temp.isEnd=true;
	}
	
	public boolean search(String word)
	{
		TrieNode temp=searchWord(word);
		if(temp!=null)
		{
			if(temp.isEnd)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public TrieNode searchWord(String word)
	{
		TrieNode temp=root;
		for(int i=0;i<word.length();i++)
		{
			char letter=word.charAt(i);
			int index=letter-'a';
			if(temp.arr[index]==null)
			{
				return null;
			}
			else
			{
				temp=temp.arr[index];
			}
		}
		return temp;
	}
	
	public boolean startsWith(String prefix)
	{
		TrieNode temp=searchWord(prefix);
		if(temp!=null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void printWords(TrieNode start,String prefix)
	{
		if(start==null)
		{
			return;
		}
		if(start.isEnd)
		{
			if(letter_count>=0)
			{
				words_selected[word_count]=prefix+(String.valueOf(letters)).substring(0,letter_count);
			}
			word_count++;
		}
		for(int i=0;i<26;i++)
		{
			if(start.arr[i]!=null){
				letters[letter_count]=(char)('a' + i);
				letter_count++;
				//letters[letter_count]='\0';
				printWords(start.arr[i],prefix);
				
			}
		}
		letter_count--;
		
	}
	
	public int wordsWithSamePrefix(String word)
	{
		TrieNode temp=root;
		
		for(int i=0;i<word.length();i++)
		{
			char letter=word.charAt(i);
			int index=letter-'a';
			if(temp.arr[index]!= null)
			{
				temp=temp.arr[index];
			}
			else
			{
				return 0;
			}
		}
		printWords(temp,word);
		return 1;
	}
	
}


public class AutoCompletion {

	static Trie trie;
	
	public static void main(String[] args) throws Exception {
	 	
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame();
        frame.setTitle("AUTO COMPLETION");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200, 200, 500, 400);

        JTextField txtInput = new JTextField();
        String str = txtInput.getText();
        trie =new Trie();
        trie.wordsWithSamePrefix(str);
        ArrayList<String> selected_words = new ArrayList<String>();
        for(int j=0;j<(trie.words_selected).length;j++)
        {
        	String item = trie.words_selected[j];
        	selected_words.add(item);
        }
        setupAutoCompletion(txtInput, selected_words);
        txtInput.setColumns(30);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(txtInput, BorderLayout.NORTH);
        frame.setVisible(true);
    }
	
	 private static boolean isAdjusting(JComboBox cbInput) {
	       
	    	return (boolean) cbInput.getClientProperty("is_adjusting");
	    	
	 }

	 private static void setAdjusting(JComboBox cbInput, boolean adjusting) {
	        
		 cbInput.putClientProperty("is_adjusting", adjusting);
	 }

	 public static void setupAutoCompletion(final JTextField txtInput, final ArrayList<String> selected_words) {
	         
		    final DefaultComboBoxModel model = new DefaultComboBoxModel();
	        final JComboBox cbInput = new JComboBox(model) {
	           
	        	public Dimension getPreferredSize() {
	                
	            	return new Dimension(super.getPreferredSize().width, 0);
	            }
	        };
	        cbInput.setFocusable(false);
	        setAdjusting(cbInput, false);
	        for (String item : selected_words) {
	            
	        	model.addElement(item);
	        }
	        cbInput.setSelectedItem(null);
	        cbInput.addActionListener(new ActionListener() {
	            
	        	@Override
	            public void actionPerformed(ActionEvent e) {
	                
	            	if (!isAdjusting(cbInput)) {
	                    
	            		if (cbInput.getSelectedItem() != null) {
	                        
	            			txtInput.setText(cbInput.getSelectedItem().toString());
	                    }
	                }
	            }
	        });

	        txtInput.addKeyListener(new KeyAdapter() {

	            @Override
	            public void keyPressed(KeyEvent e) {
	                
	            	setAdjusting(cbInput, true);
	                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
	                    
	                	if (cbInput.isPopupVisible()) {
	                		setAdjusting(cbInput, false);
	                		e.setKeyCode(KeyEvent.VK_ENTER);
	                    }
	                	
	                       
	                }
	                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
	                    
	                	setAdjusting(cbInput, true);
	                	e.setSource(cbInput);
	                    cbInput.dispatchEvent(e);//dispatches event onto  the target invoking
                          //the EventListeners in appropriate order		                   
	                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	                        
	                    	setAdjusting(cbInput, false);
	                    
	                    			if (cbInput.getSelectedItem() != null) {
		                        
	                    				
	                    					txtInput.setText(cbInput.getSelectedItem().toString());
	                    					cbInput.setPopupVisible(false);
	                    					
	                    	        }
	                    			
	                    }
	                }
	                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	                    
	                	setAdjusting(cbInput, true);
	                	cbInput.setPopupVisible(false);
	                }
	                setAdjusting(cbInput, false);
	            }
	        });
	        
	        txtInput.getDocument().addDocumentListener(new DocumentListener() {
	            
	        	public void insertUpdate(DocumentEvent e) {
	                
	        		try {
						
	        			updateList();
					} 
	        		catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }

	            public void removeUpdate(DocumentEvent e) {
	                
	            	try {
						
	            		updateList();
					} 
	            	catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }

	            public void changedUpdate(DocumentEvent e) {
	                
	            	try {
						
	            		updateList();
					} 
	            	catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }

	            private void updateList() throws IOException  {
	                
	            	setAdjusting(cbInput, true);
	                model.removeAllElements();
	                String input = txtInput.getText();
	                trie.refresh();
	                int flag=(trie).wordsWithSamePrefix(input);
	                if(flag!=0){ 	
	                	
	                	if(!input.isEmpty())
	                	{
	                		for(String item: (trie).words_selected)
	                		{
	                			model.addElement(item);
	                		}
	                	}
	                }
	                cbInput.setPopupVisible(model.getSize() > 0);
	                setAdjusting(cbInput, false);
	            }
	        });
	        
	        txtInput.setLayout(new BorderLayout());
	        txtInput.add(cbInput, BorderLayout.SOUTH);
	    }
	}
