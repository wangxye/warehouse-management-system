package com.jlrfid.mainframe.tagRWOperate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Timer;
import java.util.TimerTask;
import com.jlrfid.mainframe.MainStart;
import com.jlrfid.mainframe.basicOperation.BasicOperate;
import com.jlrfid.mainframe.tool.R2kUtil;
import com.jlrfid.r2ks.util.Message;

/**
 * ��ǩ��д����
 * 
 * @author zhuQixiang
 * 
 */
public class TagRWOperateOnclick extends MainStart {
	private static final long serialVersionUID = 1L;

	public static void onclick() {
		btnUnlockPerform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TagRWOperate.tagAddUnlock();
			}
		});
		btnRWWrite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TagRWOperate.writeCardInfo();
			}
		});
		btnRWClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtRWData.setText("");
				labResult.setText("");
			}
		});
		btnRWReadCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TagRWOperate.onceReaderCard();
			}
		});
		btnRWStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isContinueReadCard) {
					Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
					return;
				}
				btnRWContinuousRead.setEnabled(true);
				isContinueReadCard = false;
				btnRWStop.setEnabled(false);
				if (timer != null) {
					timer.cancel();// ʹ����������˳�����
				}
			}
		});

		cboRWDesignatedArea.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ReadWriteData.comboboxInterlock(cboRWDesignatedArea, cboRWDesignatedStartAddress,
						cboRWDesignatedLength);
			}
		});

		cboRWDesignatedStartAddress.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ReadWriteData.comboboxAddress(cboRWDesignatedArea, cboRWDesignatedStartAddress, cboRWDesignatedLength);
			}
		});
		btnDestoryTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TagRWOperate.destoryTag();
			}
		});
		btnRWContinuousRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isContinueReadCard) {
					Message.Show(rs.getString("MsgExNodevicebtnStartEnable"), "");
					return;
				}
				if (R2kUtil.r2ks[0] == null) {
					Message.Show(MainStart.rs.getString("MsgExNodevicetoconnect"), "");
					return;
				}
				btnRWContinuousRead.setEnabled(false);
				//isContinueReadCard = true;
				btnRWStop.setEnabled(true);
				txtRWData.setText("");
				counts = 1;
				timer = new Timer();
				timer.schedule(new designatedAreaReaderCard(), 1000, 2000);// ��1���ִ�д�����,ÿ�μ��2��ִ��һ��,�������һ��Data����,�Ϳ�����ĳ���̶���ʱ��ִ���������.
			}
		});
	}
}

class designatedAreaReaderCard extends TimerTask {
	public void run() {
		TagRWOperate.continueReaderCard();
	}
}
