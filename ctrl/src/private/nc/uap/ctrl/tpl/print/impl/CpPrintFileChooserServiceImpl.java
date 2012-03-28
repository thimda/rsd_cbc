package nc.uap.ctrl.tpl.print.impl;

import java.awt.HeadlessException;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import nc.bs.logging.Logger;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.ICpPrintFileChooserService;

public class CpPrintFileChooserServiceImpl implements
		ICpPrintFileChooserService {

	private JFrame frm;
	private File f;
	private JFileChooser fc;
	private int flag;
	private String readPath; //�ļ�·��
	private String fileName; //�ļ���
	private String type = ".xml";  //Ĭ����xml�ļ�
	public static final int XML = 0;
	public static final int DOCX = 1;
	
	@Override
	public String getRealPath() {
		// TODO Auto-generated method stub
		return readPath;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

		frm = new JFrame("java");
		fc = new JFileChooser();
	}

	@Override
	public void readFile() throws TplBusinessException{
		// TODO Auto-generated method stub

		FileFilterPrint fileFilter = new FileFilterPrint();
		fc.setFileFilter(fileFilter);
		// ���ñ����ļ��Ի���ı���
		fc.setDialogTitle("Save File");
		// ���ｫ��ʾ�����ļ��ĶԻ���
		try {
			flag = fc.showSaveDialog(frm);
			fc.setVisible(true);
		} catch (HeadlessException he) {
			Logger.error(he.getMessage(), he);
			throw new TplBusinessException(he.getMessage(),he);
		}
		// �������ȷ����ť�����ø��ļ���
		if (flag == JFileChooser.APPROVE_OPTION) {
			// ���������Ҫ������ļ�
			f = fc.getSelectedFile();
			// ����ļ���
			fileName = fc.getName(f);
			if(isRight(fileName)){
				if(!fileName.contains(".")){
					readPath = f.getPath()+type;
				}else{
					readPath = f.getPath();
				}
			}else{
				readFile();
			}
		}
	}
	
	private class FileFilterPrint extends javax.swing.filechooser.FileFilter{
		public boolean accept(java.io.File f) {
	        if (f.isDirectory())return true;
	        return f.getName().endsWith(type);  //����Ϊѡ����.classΪ��׺���ļ�
	      } 
	      public String getDescription(){
	        return "*"+type;
	      }
	}
	
	private boolean isRight(String name){
		if(!name.contains(".")) return true;
		return name.endsWith(type);
	}

	public String getFileName() {
		return fileName;
	}

	@Override
	public void setType(int TYPE) {
		// TODO Auto-generated method stub
		
		switch(TYPE){
		case 0:
			type = ".xml";
			break;
		case 1:
			type = ".docx";
			break;
		default:
			type = ".xml";
		}
	}

}
