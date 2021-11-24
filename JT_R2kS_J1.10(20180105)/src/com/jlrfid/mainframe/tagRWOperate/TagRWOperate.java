package com.jlrfid.mainframe.tagRWOperate;

import com.jlrfid.mainframe.MainStart;
import com.jlrfid.mainframe.tool.R2kUtil;
import com.jlrfid.r2ks.MainHandler;
import com.jlrfid.r2ks.util.Message;
import com.jlrfid.r2ks.util.Regex;

public class TagRWOperate extends MainStart {
	private static final long serialVersionUID = 1L;

	public static void setSearchArea() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"), "");
			return;
		}
		boolean isConn = false;
		try {
			byte state = 0;
			if (rdoDeviceSearchCustom.isSelected()) {
				state = 1;
			} else {
				state = 0; // DIY
			}
			boolean result = false;
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					if (rdoDeviceSearchEPC.isSelected()) {
						result = MainHandler.setReadZone(R2kUtil.r2ks[i], state);
						if (result) {
							Message.Show(rs.getString("MsgSetSuccessfindcardarea"), "");// "����Ѱ������ɹ�");
							return;
						} else {
							Message.Show(rs.getString("MsgSetfailurefindcardarea"), "");// "����Ѱ������ʧ��");
							return;
						}
					} else if (rdoDeviceSearchCustom.isSelected()) {
						if (-1 == cboDeviceSearchArea.getSelectedIndex()) {
							Message.Show(rs.getString("Msgselectcardarea"), "");// "��ѡ��Ѱ������");
							return;
						}
						if (-1 == cboDeviceSearchStartAddr.getSelectedIndex()) {
							Message.Show(rs.getString("Msgselectstartingaddress"), "");// "��ѡ����ʼ��ַ");
							return;
						}
						if (-1 == cboDeviceSearchLen.getSelectedIndex()) {
							Message.Show(rs.getString("Msgselectthelength"), "");// "��ѡ�񳤶�");
							return;
						}
						byte bank = (byte) (cboDeviceSearchArea.getSelectedIndex() + 1);
						byte begin = (byte) (cboDeviceSearchStartAddr.getSelectedIndex());
						byte length = (byte) (cboDeviceSearchLen.getSelectedIndex() + 1);
						if (1 == bank) { // EPC
							if (begin < 2 || begin > 7) {
								Message.Show(rs.getString("MsgEPCStartingaddressisinvalid"), "");// "EPC��ʼ��ַ��Ч");
								return;
							}
							if (length > 6) {
								Message.Show(rs.getString("MsgEPCareamaxlengthof6char"), "");// "EPC����󳤶�6����");
								return;
							}
						}
						if (2 == bank) // TID
						{
							if (begin > 5) {
								Message.Show(rs.getString("MsgTIDStartingaddressisinvalid"), "");// "TID����ʼ��ַ��Ч");
								return;
							}
							if (length > 6) {
								Message.Show(rs.getString("MsgTIDCareamaxlengthof6char"), "");// "TID����󳤶�6����");
								return;
							}
						}
						result = MainHandler.setReadZone(R2kUtil.r2ks[i], state);
						boolean total = MainHandler.setReadZonePara(R2kUtil.r2ks[i], bank, begin, length);
						if (result && total) {
							Message.Show(rs.getString("MsgSetcardareafoundsuccess"), "");// "����Ѱ������ɹ�");
						} else {
							Message.Show(rs.getString("MsgSetcardfailureareaparam"), "");// "����Ѱ���������ʧ��");
						}
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExDeviceSearchSet"), "");
		}
	}

	public static void destoryTag() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		String strAccessPwd = txtRWAccessPwd.getText().replace(" ", "");
		String strKillPwd = txtDestoryPassword.getText().replace(" ", "");
		if (strAccessPwd.length() < 1) {
			Message.Show(rs.getString("strMsgAccessPasswordIsEmpty"), "");
			return;
		}
		if (strKillPwd.length() < 1) {
			Message.Show(rs.getString("strMsgKillPasswordIsEmpty"), "");
			return;
		}
		if (!Regex.isHexCharacter(strAccessPwd) || !Regex.isHexCharacter(strKillPwd)) {
			Message.Show(rs.getString("strMsgPwdInvalidChar"), "");
			return;
		}
		if (strAccessPwd.length() != 8 || strKillPwd.length() != 8) {
			Message.Show(rs.getString("strMsgLengthDiff"), "");
			return;
		}
		byte[] byteAccessPwd = new byte[4];
		byte[] byteKillPwd = new byte[4];
		for (int i = 0; i < 4; ++i) {
			String accessPwd = strAccessPwd.substring(i * 2, (2 + i * 2));
			String killPwd = strKillPwd.substring(i * 2, (2 + i * 2));
			byteAccessPwd[i] = Integer.valueOf(accessPwd, 16).byteValue();
			byteKillPwd[i] = Integer.valueOf(killPwd, 16).byteValue();
		}
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					boolean result = MainHandler.killTag(R2kUtil.r2ks[i], byteAccessPwd, byteKillPwd);
					if (result) {
						Message.Show(rs.getString("strMsgSucceedDes"), "");
					} else {
						Message.Show(rs.getString("strMsgFailedDes"), "");
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("strMsgFailedDes"), "");
		}
	}

	/**
	 * д�뿨��Ϣ
	 */
	public static void writeCardInfo() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		if (R2kUtil.r2ks[0] == null) {
			Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			return;
		}
		boolean isConn = false;
		labResult.setText("");
		String pwd = txtRWAccessPwd.getText().replace(" ", "").toString();
		byte bank = (byte) cboRWDesignatedArea.getSelectedIndex();
		byte begin = (byte) Integer.parseInt(cboRWDesignatedStartAddress.getSelectedItem().toString());
		byte size = (byte) Integer.parseInt(cboRWDesignatedLength.getSelectedItem().toString());
		String inData = txtRWData.getText().replace(" ", "");
		if (pwd.length() < 1) {
			Message.Show(rs.getString("strMsgAccessPasswordIsEmpty"), "");
			return;
		}
		if (inData.length() % 4 != 0 || inData.length() / 4 != size) {
			Message.Show(rs.getString("MsgActualAndSpecifyDataDiff"), "");// "ʵ�����ݳ��Ⱥ�ָ���ĳ��Ȳ�ͬ");
			return;
		}
		if (!Regex.isHexCharacter(inData)) {
			Message.Show(rs.getString("MsgDataContainsOutside"), "");// "�����к���0��9��A��F֮��ķǷ��ַ�");
			return;
		}
		byte[] password = new byte[4];
		for (int i = 0; i < password.length; i++) {
			String str = pwd.substring(i * 2, (2 + i * 2));
			// ���ַ������Ӵ�תΪ16���Ƶ�8λ�޷�������
			password[i] = Integer.valueOf(str, 16).byteValue();
		}
		if (btnStart.isEnabled() == false) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"), "");
			return;
		}
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					boolean result = MainHandler.writeTagData(R2kUtil.r2ks[i], bank, begin, size, inData, password);
					if (result) {
						labResult.setText(rs.getString("MsgWriteToSuccessful"));// "д��ɹ�";
					} else {
						labResult.setText(rs.getString("MsgWriteToFaled"));// "д��ʧ��";
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception e1) {
			Message.Show(rs.getString("MsgExWriteTagData"), "");
		}
	}

	public static void tagAddUnlock() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"), "");
			return;
		}
		boolean isConn = false;
		try {
			int lockBank = -1;
			int locktType = -1;
			labResult.setText("");
			if ((lockBank = cbbUnlockArea.getSelectedIndex()) == -1) {
				Message.Show(rs.getString("strMsgSelecOprBank"), "");// "��ѡ��Ҫ����������");
				return;
			}
			if ((locktType = cbbUnlockType.getSelectedIndex()) == -1) {
				Message.Show(rs.getString("strMsgSelectactiontype"), "");// "��ѡ���������");
				return;
			}
			String strpwd = txtRWAccessPwd.getText().replace(" ", "");
			if (strpwd.length() != 8) {
				Message.Show(rs.getString("strMsgPwdMustEight"), "");// "�������Ϊ8λ");
				return;
			}
			if (!Regex.isHexCharacter(strpwd)) {
				Message.Show(rs.getString("strMsgPwdInvalidChar"), "");// "���뺬��0��9��A��F֮��ķǷ��ַ�");
				return;
			}
			byte[] pwd = new byte[4];
			for (int i = 0; i < 4; ++i) {
				String str = strpwd.substring(i * 2, (2 + i * 2));
				// ���ַ������Ӵ�תΪ16���Ƶ�8λ�޷�������
				pwd[i] = Byte.parseByte(str, 16);
			}

			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					boolean result = MainHandler.lockTag(R2kUtil.r2ks[i], (byte) locktType, (byte) lockBank, pwd);
					if (result) {
						labResult.setText(rs.getString("MsgActionSuccessful"));// "�����ɹ�";
					} else {
						labResult.setText(rs.getString("MsgActionFaled"));// "����ʧ��";
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExLockTag"), "");
		}
	}

	public static void onceReaderCard() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		boolean isConn = false;
		try {
			int RWBank = -1;
			int startAdd = -1;
			int length = -1;
			labResult.setText("");
			RWBank = cboRWDesignatedArea.getSelectedIndex();
			if (RWBank == -1) {
				Message.Show(rs.getString("MsgChangeRWArea"), "");// "ѡ���д����"
				return;
			}
			startAdd = Integer.parseInt(cboRWDesignatedStartAddress.getSelectedItem().toString());
			if (startAdd == -1) {
				Message.Show(rs.getString("MsgChangeStartAddr"), "");// "ѡ����ʼ��ַ";
				return;
			}
			length = Integer.parseInt(cboRWDesignatedLength.getSelectedItem().toString());
			if (length == -1) {
				Message.Show(rs.getString("MsgChangeEffectiveLen"), "");// "ѡ����Ч����");
				return;
			}
			String strpwd = txtRWAccessPwd.getText().replace(" ", "");
			if (strpwd.length() != 8) {
				Message.Show(rs.getString("strMsgPwdMustEight"), "");// "�������Ϊ8λ");
				return;
			}
			if (!Regex.isHexCharacter(strpwd)) {
				Message.Show(rs.getString("strMsgPwdInvalidChar"), "");// "�����к���0��9��A��F֮��ķǷ��ַ�");
				return;
			}
			// ת�������ַ�תΪbyte����
			byte[] pwd = new byte[4];
			for (int i = 0; i < 4; ++i) {
				String str = strpwd.substring(i * 2, (2 + i * 2));
				// ���ַ������Ӵ�תΪ16���Ƶ�8λ�޷�������
				pwd[i] = Byte.parseByte(str, 16);
			}
			// byte[] byteArray = new byte[64];// ���ջ�����
			if (btnStart.isEnabled() == false) {
				Message.Show(rs.getString("MsgExNodevicebtnStartEnable"), "");
				return;
			}
			txtRWData.setText("");
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					String readCard = MainHandler.readTagData(R2kUtil.r2ks[i], (byte) RWBank, (byte) startAdd,
							(byte) length, pwd);
					if (null != readCard) {
						readCard = cardAddEmpity(readCard);
						txtRWData.setText(txtRWData.getText() + readCard);
						labResult.setText(rs.getString("MsgReadDataSuccess"));// "�����ݳɹ�";
					} else {
						labResult.setText(rs.getString("MsgReadDataFailure"));// "������ʧ��";
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception e) {
			Message.Show(rs.getString("MsgExReadTagData"), "");
		}
	}

	public static void continueReaderCard() {
		boolean isConn = false;
		try {
			int RWBank = -1;
			int startAdd = -1;
			int length = -1;
			labResult.setText("");
			RWBank = cboRWDesignatedArea.getSelectedIndex();
			if (RWBank == -1) {
				Message.Show(MainStart.rs.getString("MsgChangeRWArea"), "");// "ѡ���д����"
				return;
			}
			startAdd = Integer.parseInt(cboRWDesignatedStartAddress.getSelectedItem().toString());
			if (startAdd == -1) {
				Message.Show(MainStart.rs.getString("MsgChangeStartAddr"), "");// "ѡ����ʼ��ַ";
				return;
			}
			length = Integer.parseInt(cboRWDesignatedLength.getSelectedItem().toString());
			if (length == -1) {
				Message.Show(MainStart.rs.getString("MsgChangeEffectiveLen"), "");// "ѡ����Ч����");
				return;
			}
			String strpwd = txtRWAccessPwd.getText().replace(" ", "");
			if (strpwd.length() != 8) {
				Message.Show(MainStart.rs.getString("strMsgPwdMustEight"), "");// "�������Ϊ8λ");
				return;
			}
			if (!Regex.isHexCharacter(strpwd)) {
				Message.Show(MainStart.rs.getString("strMsgPwdInvalidChar"), "");// "�����к���0��9��A��F֮��ķǷ��ַ�");
				return;
			}
			// ת�������ַ�תΪbyte����
			byte[] pwd = new byte[4];
			for (int i = 0; i < 4; ++i) {
				String str = strpwd.substring(i * 2, (2 + i * 2));
				// ���ַ������Ӵ�תΪ16���Ƶ�8λ�޷�������
				pwd[i] = Byte.parseByte(str, 16);
			}
			byte[] byteArray = new byte[64];// ���ջ�����
			// timerConnRead.Enabled = true; //����������ȡ��ʱ��
			// timerConnRead.Interval = 1500;
			if (btnStart.isSelected()) {
				Message.Show(MainStart.rs.getString("MsgExNodevicebtnStartEnable"), "");
				return;
			}
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					String readCard = MainHandler.readTagData(R2kUtil.r2ks[i], (byte) RWBank, (byte) startAdd,
							(byte) length, pwd);
					if (null != readCard) {
						readCard = cardAddEmpity(readCard);
						if (txtRWData.getText().equals("")) {
							txtRWData.setText(counts + "." + " " + readCard);
							counts = counts + 1;
						} else {
							txtRWData.setText(txtRWData.getText() + "\r" + "\n" + counts + "." + " " + readCard);
							counts = counts + 1;
						}
						labResult.setText(MainStart.rs.getString("MsgReadDataSuccess"));// "�����ݳɹ�";
					} else {
						labResult.setText(MainStart.rs.getString("MsgReadDataFailure"));// "������ʧ��";
					}
				}
			}
			if (!isConn) {
				Message.Show(MainStart.rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception e) {
			Message.Show(MainStart.rs.getString("MsgExReadTagData"), "");
		}
	}
}
