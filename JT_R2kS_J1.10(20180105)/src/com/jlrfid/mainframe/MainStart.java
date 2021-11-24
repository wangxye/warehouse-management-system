package com.jlrfid.mainframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import com.eltima.components.ui.DatePicker;
import com.jlrfid.mainframe.basicOperation.BasicOperateOnclick;
import com.jlrfid.mainframe.basicOperation.BasicTable;
import com.jlrfid.mainframe.basicOperation.BasicTree;
import com.jlrfid.mainframe.basicOperation.IPTextField;
import com.jlrfid.mainframe.commSetupNetwork.CommParamsSettingOnclick;
import com.jlrfid.mainframe.commSetupNetwork.CommTable;
import com.jlrfid.mainframe.deviceParams.AntennaParameter;
import com.jlrfid.mainframe.deviceParams.DeviceParamsOnclick;
import com.jlrfid.mainframe.language.LanguageSet;
import com.jlrfid.mainframe.tagRWOperate.ReadWriteData;
import com.jlrfid.mainframe.tagRWOperate.TagRWOperateOnclick;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings({ "unchecked", "rawtypes"})
public class MainStart extends JFrame {
	public static final long serialVersionUID = 1L;
	/**
	 * main 容器
	 */
	private JPanel contentPane;
	/**
	 * main panel
	 */
	public static  JPanel panel_main;
	/**
	 * 多页选项板
	 */
	public static JTabbedPane tabbedPane;
	/**
	 * 显示语言panel
	 */
	public static JPanel panel_language;
	/**
	 * 显示语言
	 */
	public static JLabel lbl_language;
	/**
	 * 用中文和英文切换下拉框的内容
	 */
	public static String[] language = { "简体中文", "English" };
	/**
	 * 中文和英文组合框
	 */
	public static JComboBox cbb_language;
	/**
	 * 默认加载中文
	 */
	public static ResourceBundle rs = ResourceBundle.getBundle("language",Locale.CHINA);
    /**************************************************************************
	 * 基本操作 start
	 *************************************************************************/
	/**
	 * 通用top panel
	 */
	public static JPanel panel_general;
	/***********************************   
	 * 通用left
	 ***********************************/
	/**
	 * 通用left panel
	 */
	public static JPanel panel_generalLeft;
	/**
	 * 通讯模式
	 */
	public static JPanel panel_CommunicationMode;
	/**
	 * 通讯模式-串口
	 */
	public static JRadioButton rdoSerialPort;
	/**
	 * 通讯模式-网口
	 */
	public static JRadioButton rdoNet;
	/**
	 * 通讯模式-刷新
	 */
	public static JButton btnRefresh;
	/**
	 * 在线设备panel
	 */
	public static JPanel panel_onlineDevice;
	/**
	 * 在线设备滚动条
	 */
	public static JScrollPane scrollPane_tree;
	/**
	 * 树形显示通讯方式
	 */
	public static JTree tree_onlineDevice ;
	/**
	 * 显示通讯方式IP或COM
	 */
	public static DefaultTableModel tableModel;
	/**
	 * 
	 */
	public static DefaultMutableTreeNode[] nodeTree = null;
	/**
	 * 在线设备树形顶级
	 */
	public static DefaultMutableTreeNode node_1 = new DefaultMutableTreeNode(MainStart.rs.getString("LVOnlineEquipment"));
	/**
	 * 在线设备的树形默认模式
	 */
	public static DefaultTreeModel model = new DefaultTreeModel(node_1, true);
	/**
	 * 在线设备树形二级
	 */
	public static DefaultMutableTreeNode node_2 = new DefaultMutableTreeNode(MainStart.rs.getString("LVOnlineEquipment"));
	/**
	 * 
	 */
	public static DefaultMutableTreeNode note = new DefaultMutableTreeNode();
	/**
	 * 获取选中节点的父节点
	 */
	public static DefaultMutableTreeNode parent = new DefaultMutableTreeNode();
	/**
	 * 新增的IP地址
	 */
	public static  JPanel panel_newAddIPAdress;
	/**
	 * IP输入框
	 */
	public static  IPTextField txtIP;
	/**
	 * 新增IP
	 */
	public static  JButton btnAddIP;
	/**
	 * 通讯方式连接和断开按扭
	 */
	public static JPanel panel_connectAndDisconnect;
	/**
	 * 通讯方式-连接
	 */
	public static JButton btnConnect;
	/**
	 * 通讯方式-断开
	 */
	public static JButton btnDisconnect;
	/***********************************
	 * 通用right
	 ***********************************/
	/**
	 * 通用right panel
	 */
	public static JPanel panel_generalRight;
	/**
	 * 显示标签panel
	 */
	public static JPanel panel_readerData;
	/**
	 * 标签个数panel
	 */
	public static JPanel panel_readerCount;
	/**
	 * 标签数
	 */
	public static JLabel labelNub;
	/**
	 * 读标签数
	 */
	public static JLabel labelTagCount;
	/**
	 * 读取标签次数panel
	 */
	public static JPanel panel_readerTagCount;
	/**
	 * 读标签数
	 */
	public static JLabel labelReadCount;
	/**
	 * 读取标签次数
	 */
	public static JLabel labelCount;
	/***************************
	 * 显示标签信息
	 **************************/
	/**
	 * 显示标签信息panel
	 */
	public static JPanel panel_tableDataShow;
	/**
	 * 显示标签信息滚动条
	 */
	public static JScrollPane sp_showTagInfo;
	/**
	 * 显示标签信息table
	 */
	public static JTable tbl_showTagInfo;
	/**
	 * 选择保存文件
	 */
	public static boolean chooseSaveFile;
	/**
	 * 保存为文件
	 */
	public static JCheckBox chkSaveFile;
	/**
	 * 读卡操作相关panel
	 */
	public static JPanel panel_operationData;
	/**
	 * 寻卡一次
	 */
	public static JButton btnInventoryOnce;
	/**
	 * 连续读卡
	 */
	public static JButton btnStart;
	/**
	 * 停止读卡
	 */
	public static JButton btnStop;
	/**
	 * 清空数据
	 */
	public static JButton btnClearData;
	/**
	 * 清空缓存
	 */
	public static JButton btnClearBuffer;
	/**
	 * 读取缓存
	 */
	public static JButton btnReadBuffer;
	/**************************************************************************
	* 基本操作 end
	*************************************************************************/
	/**************************************************************************
	* 标签操作start
	*************************************************************************/
	/**
	 * 标签操作top panel
	 */
	public static JPanel panel_tagAccess;
	/**
	 * 标签操作panel
	 */
	public static JPanel panel_tagOperation;
	/********************
	 * 快速读写
	 *******************/
	public static JPanel panel_fastReadWrite;
	/**
	 * 快写数据
	 */
	public static JLabel labFastReadWrite;
	/**
	 * 读取EPC数据
	 */
	public static JButton btnEpc;
	/**
	 * 快写标签
	 */
	public static JButton btnFastWrite;
	/**
	 * 快写标签滚动条
	 */
	public static JScrollPane scrollPane;
	/**
	 * 快写标签
	 */
	public static JTextArea tfFastReadWrite;
	/*******************
	 * 指定区域读/写 panel
	 ******************/
	public static JPanel panel_Designatedarea;
	/**
	 * 读/写区域
	 */
	public static JLabel lblRWArea;
	/**
	 * 读/写数据
	 */
	public static JLabel lblRWData;
	/**
	 * 读/写_访问密码
	 */
	public static JLabel lblRWAccessPwd;
	/**
	 * 读/写_访问密码
	 */
	public static JTextField txtRWAccessPwd;
	/**
	 * 读/写操作区域panel
	 */
	public static JPanel panel_RWOperationArea;
	/**
	 * 指定区域
	 */
	public static JComboBox cboRWDesignatedArea;
	/**
	 * 起始地址
	 */
	public static JLabel lblRWStartAddress;
	/**
	 * 指定的起始地址
	 */
	public static JComboBox cboRWDesignatedStartAddress;
	/**
	 * 指定的长度
	 */
	public static JLabel lblRWLength;
	/**
	 * 指定的长度
	 */
	public static JComboBox cboRWDesignatedLength;
	/**
	 * 数据显示区域滚动条
	 */
	public static JScrollPane spRWData;
	/**
	 * 数据显示区域
	 */
	public static JTextArea txtRWData;
	/**
	 * 指定读写区域按扭panel
	 */
	public static JPanel panel_Designated;
	/**
	 * 指定读写区域读取
	 */
	public static JButton btnRWReadCard;
	/**
	 * 指定读写区域清除
	 */
	public static JButton btnRWClear;
	/**
	 * 指定读写区域写入
	 */
	public static JButton btnRWWrite;
	/**
	 * 指定读写区域连续和停止
	 */
	public static JPanel panel_RWOperationButton;
	/**
	 * 连续读卡计数
	 */
	public static int counts = 1;
	/**
	 * 指定读写区域连续读卡
	 */
	public static JButton btnRWContinuousRead;
	/**
	 * 指定读写区域停止
	 */
	public static JButton btnRWStop;
	/**
	 * 连续读卡定时器
	 */
	public static Timer timer = null;
	/***************************
	 * 标签加解锁
	 ***************************/
	public static JPanel panel_TagAddLockAndUnlock;
	/**
	 * 访问密码
	 */
	public static JLabel lblLockAccessPwd;
	/**
	 * 访问密码
	 */
	public static JTextField tfLockAccessPwd;
	/**
	 * 灭活密码
	 */
	public static JLabel lblKillPwd;
	/**
	 * 灭活密码
	 */
	public static JTextField tfKillPwd;
	/**
	 * 高级操作
	 */
	public static JPanel panel_advancedOperation;
	/**
	 * 锁卡区域
	 */
	public static JLabel labLockBank;
	/**
	 * 锁卡区域
	 */
	public static JComboBox cbbLockBank;
	/**
	 * 锁毁标签
	 */
	public static JButton btnKillTag;
	/**
	 * 标签加解锁
	 */
	public static JPanel panel_tagUnlockAndLock;
	/**
	 * 标签加解锁_操作类型
	 */
	public static JLabel lblUnlockType;
	/**
	 * 标签加解锁_操作类型
	 */
	public static JComboBox cbbUnlockType;
	/**
	 * 标签加解锁_区域
	 */
	public static JLabel lblUnlockArea;
	/**
	 * 标签加解锁_区域
	 */
	public static JComboBox cbbUnlockArea;
	/**
	 * 标签解锁
	 */
	public static JButton btnUnlockTag;
	/**
	 * 标签加解锁_执行
	 */
	public static JButton btnUnlockPerform;
	/**
	 * 标签初始化
	 */
	public static JButton btnInitTag;
	/**
	 * 标签加锁
	 */
	public static JButton btnLockTag;
	/**
	 * 锁毁标签
	 */
	public static JPanel panel_destoryTag;
	/**
	 * 锁毁标签_灭活密码
	 */
	public static JLabel lblDestoryPassword;
	/**
	 * 锁毁标签_灭活密码
	 */
	public static JTextField txtDestoryPassword;
	/**
	 * 锁毁标签_锁毁标签
	 */
	public static JButton btnDestoryTag;
	/**
	 * 标签操作显示信息
	 */
	public static  JLabel labResult;
	/**************************************************************************
	* 标签操作end
	*************************************************************************/
	/**************************************************************************
	* 通讯参数设置start
	*************************************************************************/
	/**
	 * 通讯参数设置top panel
	 */
	public static JPanel panel_commParamsSetting;
	/**
	 * 通讯参数设置panel
	 */
	public static JPanel panel_communicationParameterSetting;
	/**
	 * 网口IP显示panel
	 */
	public static JPanel panel_showIPInfo;
	/**
	 * 网口IP显示滚动条
	 */
	public static JScrollPane sp_showIPInfo;
	/**
	 * 网口IP显示
	 */
	public static JTable table_ZL;
	/**
	 * 卓岚IP显示table
	 */
	public static DefaultTableModel ZLtableModel;
	/**
	 * 搜索设备和编辑设备panel
	 */
	public static  JPanel panel_searchAndEditDeivce;
	/**
	 * 编辑设备
	 */
	public static  JButton btnEditDevice;
	/**
	 * 搜索设备
	 */
	public static  JButton btnSearchDevice;
	/**
	 * 串口参数
	 */
	public static JPanel panel_SerialPortParams;
	/**
	 * 串口参数设置
	 */
	public static JPanel panel_SerialPortParamsSet;
	/**
	 * 串口参数设置-波特率
	 */
	public static JLabel lblBaudRate;
	/**
	 * 串口参数设置-波特率
	 */
	public static JComboBox cbbSerialPortBaudRate;
	/**
	 * 校验位
	 */
	public static JLabel lblParity;
	/**
	 * 校验位
	 */
	public static JComboBox cbbParity;
	/**
	 * 数据位panel
	 */
	public static JPanel panel_SerialPortSet;
	/**
	 * 数据位
	 */
	public static JLabel lblDataBits;
	/**
	 * 数据位
	 */
	public static JComboBox cboDataBits;
	/**********************
	 * 网络参数
	 **********************/
	public static JPanel panel_NetParams;
	/**
	 * 网络参数标题
	 */
	public static JPanel panel_networkParamTitle;
	/**
	 * 网络模式
	 */
	public static  JLabel lblNetworkMode;
	/**
	 * IP模式
	 */
	public static  JLabel lblIPMode;
	/**
	 * IP地址
	 */
	public static  JLabel lblIPAddress;
	/**
	 * 子网掩码
	 */
	public static  JLabel lblSubnetMask;
	/**
	 * 端口
	 */
	public static  JLabel lblPort;
	/**
	 * 网关
	 */
	public static  JLabel lblGateway;
	/**
	 * 网络参数文本框panel
	 */
	public static JPanel panel_networkParamText;
	/**
	 * 网络模式
	 */
	public static JComboBox cbbNetworkMode;
	/**
	 * IP模式
	 */
	public static JComboBox cbbIPMode;
	/**
	 * IP地址
	 */
	public static JTextField txtIPAddress;
	/**
	 * 子网掩码
	 */
	public static JTextField txtSubnetMask;
	/**
	 * 端口
	 */
	public static JTextField txtPort;
	/**
	 * 网关
	 */
	public static JTextField txtGateway;
	/**
	 * 适用于TCP客户端模式
	 */
	public static JLabel lblPromotion;
	/**
	 * 客户端标题
	 */
	public static JPanel panel_clientTitle;
	/**
	 * 目的IP
	 */
	public static JLabel lblDestinationIP;
	/**
	 * 目的端口
	 */
	public static JLabel lblDestinationPort;
	/**
	 * 客户端文本框
	 */
	public static JPanel panel_clientText;
	/**
	 * 目的端口
	 */
	public static JTextField txtDestinationPort;
	/**
	 * 目的IP
	 */
	public static JTextField txtDestinationIP;
	/**
	 * 网络参数按扭panel
	 */
	public static JPanel panel_networkParamButton;
	/**
	 * 默认参数
	 */
	public static JButton btnDefaultParams;
	/**
	 * 设置参数
	 */
	public static JButton btnSetParams;
	/**************************************************************************
	* 通讯参数设置end
	*************************************************************************/
	/**************************************************************************
	* 设备参数start
	*************************************************************************/
	/**
	 * 设备参数
	 */
	public static JPanel panel_parametersSetup;
	/**********************
	 * 设备参数left_panel_start
	 **********************/
	/**
	 * 设备参数left_panel
	 */
	public static JPanel panel_deviceParamLeft;
	/**
	 * 显示信息
	 */
	public static Label labelShowInfo;
	/**********************
	 * 设备天线panel_start
	 **********************/
	public static JPanel panel_DeviceAntenna;
	/**
	 * 工作时间
	 */
	public static JLabel lblDeviceAntennaWorkTime;
	/**
	 * 功率
	 */
	public static JLabel lblDeviceAntennaPower;
	/**
	 * 天线参数容器
	 */
	public static JPanel panel_antennaParamsContent;
	
	/**********************
	 * 设备天线panel_end
	 **********************/
	/**
	 * 天线1
	 */
	public static JCheckBox chkDeviceAntenna1;
	/**
	 * 天线2
	 */
	public static JCheckBox chkDeviceAntenna2;
	/**
	 * 天线3
	 */
	public static JCheckBox chkDeviceAntenna3;
	/**
	 * 天线4
	 */
	public static JCheckBox chkDeviceAntenna4;
	/**
	 * 工作时间1
	 */
	public static JComboBox cboDeviceAntennaWorkTime1;
	/**
	 * 工作时间2
	 */
	public static JComboBox cboDeviceAntennaWorkTime2;
	/**
	 * 工作时间3
	 */
	public static JComboBox cboDeviceAntennaWorkTime3;
	/**
	 * 工作时间4
	 */
	public static JComboBox cboDeviceAntennaWorkTime4;
	/**
	 * 功率1
	 */
	public static JComboBox cboDeviceAntennaPower1;
	/**
	 * 功率2
	 */
	public static JComboBox cboDeviceAntennaPower2;
	/**
	 * 功率3
	 */
	public static JComboBox cboDeviceAntennaPower3;
	/**
	 * 功率4
	 */
	public static JComboBox cboDeviceAntennaPower4;
	/**
	 * 天线设置按扭
	 */
	public static JPanel panel_antennaParamsButton;
	/**
	 * 天线读取
	 */
	public static JButton btnDeviceAntennaRead;
	/**
	 * 天线设置
	 */
	public static JButton btnDeviceAntannaSet;
	/**
	 * 声音设置
	 */
	public static JPanel panel_DeviceBuzzer;
	/**
	 * 声音设置
	 */
	public static JPanel panel_buzzSetContent;
	/**
	 * 声音设置_打开
	 */
	public static JRadioButton rdoDeviceBuzzerOpen;
	/**
	 * 声音设置_关闭
	 */
	public static JRadioButton rdoDeviceBuzzerClose;
	/**
	 * 声音设置_读取
	 */
	public static JButton btnDeviceBuzzerRead;
	/**
	 *  声音设置_设置
	 */
	public static JButton btnDeviceBuzzerSet;
	/**
	 * 设置输出口
	 */
	public static JPanel panel_SetOutPort;
	/**
	 * 输出口设置
	 */
	public static JButton btnLabelOutSet;
	/**
	 * 输出口_打开或关闭
	 */
	public static JPanel panel_setOutPortOpenAndClose;
	/**
	 * 输出口_打开
	 */
	public static JRadioButton rdoOutOpen;
	/**
	 * 输出口_关闭
	 */
	public static JRadioButton rdoOutClose;
	/**
	 * 输出口
	 */
	public static JComboBox cboOutPort;
	/**
	 * 输出口_端口号
	 */
	public static JLabel lblOutPort;
	/**
	 * 工作模式
	 */
	public static JPanel panel_DeviceWork;
	/**
	 * 工作模式second_panel
	 */
	public static JPanel panel_workMode;
	/**
	 *  工作模式_占格用布局
	 */
	public static JLabel lbltemp;
	/**
	 * 设备工作模式
	 */
	public static JComboBox cboDeviceWorkMode;
	/**
	 * 工作模式_读取
	 */
	public static JButton btnDeviceWorkRead;
	/**
	 * 工作模式_设置
	 */
	public static JButton btnDeviceWorkSet;
	/**
	 * 工作模式_触发延时
	 */
	public static JTextField txtDeviceWorkDelay;
	/**
	 * 工作模式_触发延时
	 */
	public static JLabel lblDeviceWorkDelay;
	/**********************
	 * 设备参数left_panel_end
	 **********************/
	/**********************
	 * 设备参数right_panel_start
	 **********************/
	/**
	 * 设备参数right_panel
	 */
	public static JPanel panel_deviceParamRight;
	/**
	 * 相邻判别
	 */
	public static JPanel panel_DeviceAdjacent;
	/**
	 * 相邻判别_时间
	 */
	public static JLabel lblDeviceAdjacentTime;
	/**
	 * 相邻判别_时间
	 */
	public static JPanel panel_DataFiltering;
	/**
	 * 相邻判别_时间
	 */
	public static JTextField txtDeviceAdjacentTime;
	/**
	 * 相邻判别_读取
	 */
	public static JButton btnDeviceAdjacentRead;
	/**
	 * 相邻判别_设置
	 */
	public static JButton btnDeviceAdjacentSet;
	/**
	 * 设备号
	 */
	public static JPanel panel_Device;
	/**
	 * 设备号_有效值
	 */
	public static JLabel lblDeviceValid;
	/**
	 * 设备号panel
	 */
	public static JPanel panel_DeviceNo;
	/**
	 * 设备号_有效值
	 */
	public static JTextField txtDeviceValid;
	/**
	 * 设备号_设置
	 */
	public static JButton btnDeviceSet;
	/**
	 * 设备号_读取
	 */
	public static JButton btnDeviceRead;
	/**
	 * 设备时钟
	 */
	public static JPanel panel_DeviceTime;
	/**
	 * 时间获取
	 */
	public static DatePicker datepick;
	/**
	 * 设备当前时间
	 */
	public static JButton btnDeviceCurrTime;
	/**
	 * 时钟_设置和读取
	 */
	public static JPanel panel_timer;
	/**
	 * 时钟_读取
	 */
	public static JButton btnDeviceTimeRead;
	/**
	 * 时钟_读取设置
	 */
	public static JButton btnDeviceTimeSet;
	/**
	 * 寻卡区域
	 */
	public static JPanel panel_DeviceSearch;
	/**
	 * 自定义
	 */
	public static JRadioButton rdoDeviceSearchCustom;
	/**
	 * 默认EPC
	 */
	public static JRadioButton rdoDeviceSearchEPC;
	/**
	 * 寻卡区域_容器
	 */
	public static JPanel panel_readAreaContent;
	/**
	 * 寻卡区域
	 */
	public static JLabel lblDeviceSearchArea;
	/**
	 * 寻卡区域_起始地址
	 */
	public static JLabel lblDeviceSearchStartAddr;
	/**
	 * 寻卡区域_长度
	 */
	public static JLabel lblDeviceSearchLen;
	/**
	 * 寻卡区域
	 */
	public static JComboBox cboDeviceSearchArea;
	/**
	 * 寻卡区域_起始地址
	 */
	public static JComboBox cboDeviceSearchStartAddr;
	/**
	 * 占格布局用
	 */
	public static JLabel lbl_temp;
	/**
	 * 寻卡区域_长度
	 */
	public static JComboBox cboDeviceSearchLen;
	/**
	 * 寻卡区域_设置
	 */
	public static JButton btnDeviceSearchSet;
	/**
	 * 寻卡区域_读取
	 */
	public static JButton btnDeviceSearchRead;
	/**
	 * 设置输出口
	 */
	public static JPanel panel_DeviceOut;
	/**
	 * 设置输出口
	 */
	public static JComboBox cboDeviceOut;
	/**
	 * 设置输出口
	 */
	public static JPanel panel_setOut;
	/**
	 * 设置输出口_读取
	 */
	public static JButton btnDeviceOutRead;
	/**
	 * 设置输出口_设置
	 */
	public static JButton btnDeviceOutSet;
	/**********************
	 * 设备参数right_panel_end
	 **********************/
	/**************************************************************************
	* 设备参数end
	*************************************************************************/
	/**
	 * 判断是否在连续读卡
	 */
    public static boolean isContinueReadCard = false;
	/**
	 * Create the frame.
	 */
	public MainStart() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Object[] options = { "Yes", "No" };
				JOptionPane pane2 = new JOptionPane(MainStart.rs.getString("MainWindowFormClosing"),
						JOptionPane.QUESTION_MESSAGE,
						JOptionPane.YES_NO_OPTION, null, options, options[1]);
				JDialog dialog = pane2.createDialog(MainStart.rs.getString("ClosePrompt"));
				dialog.setVisible(true);
				Object selectedValue = pane2.getValue();
				if (selectedValue == null || selectedValue == options[1]) {
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 这个是关键
				} else if (selectedValue == options[0]) {
					setDefaultCloseOperation(EXIT_ON_CLOSE);
				}
			}
		});
		init();
		mainPanel();
		general();
		tagOperation();
		commParamsSetting();
		deviceParametersSetup();
		commonality(); //操作界面公共方法
		//BasicOperate.buttonSet(false);
	}

	private void commonality() {
		panel_main.add(tabbedPane);
		tabbedPane.setSelectedIndex(0);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_main, GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_main, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainStart frame = new MainStart();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 操作树形_方法未用
	 */
	public void opertaionTree() {
		tree_onlineDevice.setModel(new DefaultTreeModel(node_2));
		if (null != nodeTree && nodeTree.length > 0) {
			for (int i = 0; i < nodeTree.length; i++) {
				node_1.add(nodeTree[i]);
			}
		}
		node_2.add(node_1);
	}

	private void init() {
		setTitle("JT_R2kS_J1.10");
		// window display style
		String manager = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		try {
			UIManager.setLookAndFeel(manager);
		} catch (Exception e) {
			// e.printStackTrace();
			// Message.Show("Display window style exception",
			// "");
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 937, 553);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	private void mainPanel() {
		panel_main = new JPanel();
		contentPane.add(panel_main, BorderLayout.CENTER);
		panel_main.setLayout(null);
		panel_language = new JPanel();
		panel_language.setBounds(744, 3, 167, 26);
		panel_main.add(panel_language);
		panel_language.setLayout(new GridLayout(1, 2, 0, 0));
		lbl_language = new JLabel("Language:");
		lbl_language.setHorizontalAlignment(SwingConstants.CENTER);
		panel_language.add(lbl_language);
		cbb_language = new JComboBox(language);
		panel_language.add(cbb_language);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 10, 921, 505);
		LanguageSet.onclick();
	}
	
	/**
	 * 基本操作
	 */
	private void general() {
		panel_general = new JPanel();
		panel_general.setOpaque(false);
		panel_general.setToolTipText("");
		tabbedPane.addTab(MainStart.rs.getString("tabGeneral"), null, panel_general, null);
		panel_generalLeft = new JPanel();
		panel_generalLeft.setBounds(13, 0, 259, 471);
		panel_generalLeft.setOpaque(false);
		panel_generalRight = new JPanel();
		panel_generalRight.setBounds(290, 0, 616, 471);
		panel_generalRight.setOpaque(false);
		panel_readerData = new JPanel();
		panel_readerData.setBounds(0, 0, 616, 24);
		panel_operationData = new JPanel();
		panel_operationData.setBounds(0, 419, 616, 42);
		JPanel panel_tableDataShow = new JPanel();
		panel_tableDataShow.setBounds(0, 30, 616, 355);
		panel_tableDataShow.setLayout(new GridLayout(1, 0, 0, 0));
		sp_showTagInfo = new JScrollPane();
		panel_tableDataShow.add(sp_showTagInfo);
		String columnHeader[] = { MainStart.rs.getString("strLvHeadNo"), "EPC",
				MainStart.rs.getString("strLvHeadCount"), MainStart.rs.getString("strLvHeadAntNo"),
				MainStart.rs.getString("gopDevice") };
		// 表格模型对象
		tableModel = new DefaultTableModel(null, columnHeader);
		tbl_showTagInfo = new JTable(tableModel);
		BasicTable.setTableStyle(tbl_showTagInfo);
		sp_showTagInfo.setViewportView(tbl_showTagInfo);
		panel_operationData.setLayout(new GridLayout(1, 6, 10, 0));
		btnInventoryOnce = new JButton(MainStart.rs.getString("btnInventoryOnce"));
		btnInventoryOnce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel_operationData.add(btnInventoryOnce);
		btnStart = new JButton(MainStart.rs.getString("btnStart"));
		panel_operationData.add(btnStart);
		btnStop = new JButton(MainStart.rs.getString("btnStop"));
		panel_operationData.add(btnStop);
		btnClearData = new JButton(MainStart.rs.getString("btnClearData"));
		panel_operationData.add(btnClearData);
		btnReadBuffer = new JButton(MainStart.rs.getString("btnReadBuffer"));
		panel_operationData.add(btnReadBuffer);
		btnClearBuffer = new JButton();
		btnClearBuffer.setText(MainStart.rs.getString("btnClearBuffer"));
		panel_operationData.add(btnClearBuffer);
		panel_readerCount = new JPanel();
		panel_readerCount.setBounds(68, 0, 236, 24);
		panel_readerCount.setLayout(new GridLayout(1, 2, 0, 0));
		labelNub = new JLabel(MainStart.rs.getString("labelNub"));
		panel_readerCount.add(labelNub);
		labelNub.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTagCount = new JLabel("0");
		panel_readerCount.add(labelTagCount);
		labelTagCount.setForeground(Color.RED);
		labelTagCount.setFont(new Font("宋体", Font.PLAIN, 20));
		labelTagCount.setHorizontalAlignment(SwingConstants.CENTER);
		labelTagCount.setHorizontalTextPosition(SwingConstants.LEADING);
		panel_CommunicationMode = new JPanel();
		panel_CommunicationMode.setBounds(0, 4, 259, 70);
		panel_CommunicationMode.setToolTipText("");
		panel_CommunicationMode.setBorder(new TitledBorder(null,MainStart.rs.getString("gopCommunicationMode"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_onlineDevice = new JPanel();
		panel_onlineDevice.setBounds(0, 84, 259, 273);
		JPanel panel_connectAndDisconnect = new JPanel();
		panel_connectAndDisconnect.setBounds(10, 419, 233, 40);
		panel_newAddIPAdress = new JPanel();
		panel_newAddIPAdress.setBounds(0, 367, 259, 42);
		panel_newAddIPAdress.setLayout(null);
		txtIP = new IPTextField();
		txtIP.setBounds(9, 5, 170, 30);
		txtIP.setOpaque(false);
		panel_newAddIPAdress.add(txtIP);
		btnAddIP = new JButton(MainStart.rs.getString("btnAddIP"));
		btnAddIP.setBounds(187, 5, 66, 31);
		btnAddIP.setOpaque(false);
		panel_newAddIPAdress.add(btnAddIP);
		panel_connectAndDisconnect.setLayout(new GridLayout(1, 2, 20, 0));
		// 连接按扭
		btnConnect = new JButton(MainStart.rs.getString("btnConnect"));
		panel_connectAndDisconnect.add(btnConnect);
		btnDisconnect = new JButton(MainStart.rs.getString("btnDisconnect"));
		
		panel_connectAndDisconnect.add(btnDisconnect);
		BasicTree.getDeviceIP(model, tree_onlineDevice, nodeTree, node_1);
		rdoSerialPort = new JRadioButton(MainStart.rs.getString("cboserialport"));
		rdoSerialPort.setBounds(6, 17, 82, 40);
		panel_CommunicationMode.setLayout(null);
		panel_CommunicationMode.add(rdoSerialPort);
		rdoNet = new JRadioButton(MainStart.rs.getString("rdobtnNet"));
		rdoNet.setBounds(88, 17, 82, 40);
		rdoNet.setSelected(true);
		ButtonGroup bgCommunicationMode = new ButtonGroup();
		bgCommunicationMode.add(rdoSerialPort);
		bgCommunicationMode.add(rdoNet);
		panel_CommunicationMode.add(rdoNet);
		btnRefresh = new JButton(MainStart.rs.getString("btnRefresh"));
		btnRefresh.setBounds(170, 17, 82, 40);
		panel_general.setLayout(null);
		panel_CommunicationMode.add(btnRefresh);
		panel_general.add(panel_generalLeft);
		panel_generalLeft.setLayout(null);
		panel_generalLeft.add(panel_onlineDevice);
		panel_onlineDevice.setLayout(new GridLayout(0, 1, 0, 0));
		JScrollPane scrollPane_tree = new JScrollPane();
		panel_onlineDevice.add(scrollPane_tree);
		tree_onlineDevice = new JTree(model);
		scrollPane_tree.setViewportView(tree_onlineDevice);
		// 显示在线设备
		tree_onlineDevice.setRootVisible(true);
		// 显示收缩箭头
		tree_onlineDevice.setShowsRootHandles(true);
		// 设置Tree的选择为一次只能选择一个节点的选项
		tree_onlineDevice.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		panel_generalLeft.add(panel_newAddIPAdress);
		panel_generalLeft.add(panel_connectAndDisconnect);
		panel_generalLeft.add(panel_CommunicationMode);
		panel_general.add(panel_generalRight);
		panel_generalRight.setLayout(null);
		panel_generalRight.add(panel_readerData);
		panel_readerData.setLayout(null);
		panel_readerData.add(panel_readerCount);
		panel_readerTagCount = new JPanel();
		panel_readerTagCount.setBounds(332, 0, 236, 24);
		panel_readerTagCount.setLayout(new GridLayout(1, 0, 0, 0));
		labelReadCount = new JLabel(MainStart.rs.getString("labelReadCount"));
		panel_readerTagCount.add(labelReadCount);
		labelReadCount.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_readerData.add(panel_readerTagCount);
		labelCount = new JLabel("0");
		panel_readerTagCount.add(labelCount);
		labelCount.setForeground(Color.BLUE);
		labelCount.setFont(new Font("宋体", Font.PLAIN, 20));
		labelCount.setHorizontalAlignment(SwingConstants.CENTER);
		panel_generalRight.add(panel_tableDataShow);
		panel_generalRight.add(panel_operationData);
		// btnDisconnect.setEnabled(false);
		// btnStart.setEnabled(false);
		// btnInventoryOnce.setEnabled(false);
		// btnStop.setEnabled(false);
		 btnReadBuffer.setVisible(false);
		 btnClearBuffer.setVisible(false);
		chkSaveFile = new JCheckBox(MainStart.rs.getString("strChkSaveFile"));
		chkSaveFile.setBounds(512, 391, 104, 23);
		panel_generalRight.add(chkSaveFile);
		BasicOperateOnclick.onclick();
	}

	/**
	 * 标签操作
	 */
	private void tagOperation() {
		panel_tagAccess = new JPanel();
		tabbedPane.addTab(MainStart.rs.getString("tabTagsOperate"), null,panel_tagAccess, null);
		panel_tagOperation = new JPanel();
		panel_tagOperation.setBounds(61, 10, 785, 456);
		panel_Designatedarea = new JPanel();
		panel_Designatedarea.setBounds(0, 10, 773, 221);
		panel_Designatedarea.setBorder(new TitledBorder(null, MainStart.rs
				.getString("gopLabelDesignatedarea"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_TagAddLockAndUnlock = new JPanel();
		panel_TagAddLockAndUnlock.setBounds(1, 247, 773, 71);
		panel_TagAddLockAndUnlock.setOpaque(false);
		panel_TagAddLockAndUnlock.setBorder(new TitledBorder(null, MainStart.rs
				.getString("gopLabelUnlock"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		labResult = new JLabel("");
		labResult.setBounds(0, 420, 259, 35);
		lblRWArea = new JLabel(MainStart.rs.getString("lblLabelRWArea"));
		lblRWArea.setBounds(45, 32, 41, 15);
		lblRWData = new JLabel(MainStart.rs.getString("lblLabelRWData"));
		lblRWData.setBounds(44, 58, 38, 15);
		spRWData = new JScrollPane();
		spRWData.setBounds(100, 60, 607, 91);
		txtRWAccessPwd = new JTextField();
		txtRWAccessPwd.setBounds(116, 166, 97, 25);
		txtRWAccessPwd.setText("00000000");
		txtRWAccessPwd.setColumns(10);
		txtRWData = new JTextArea();
		spRWData.setViewportView(txtRWData);
		String[] unlockArea = { "Kill", "Access", "EPC", "TID", "User" };
		String[] unlockType = { MainStart.rs.getString("cboLabelUnlockTypeunlock"),
				MainStart.rs.getString("cboLabelUnlockTypePermanentwritable"),
				MainStart.rs.getString("cboLabelUnlockTypeSafetylock"),
				MainStart.rs.getString("cboLabelUnlockTypePermanentcannotwrite") };
		cbbUnlockType = new JComboBox();
		cbbUnlockType.setBounds(403, 24, 162, 29);
		panel_TagAddLockAndUnlock.add(cbbUnlockType);
		cbbUnlockType.setModel(new DefaultComboBoxModel(unlockType));
		btnUnlockPerform = new JButton(MainStart.rs.getString("btnLabelPerform"));
		btnUnlockPerform.setBounds(609, 21, 104, 29);
		panel_tagAccess.setLayout(null);
		panel_TagAddLockAndUnlock.setLayout(null);
		panel_advancedOperation = new JPanel();
		panel_advancedOperation.setBounds(43, 25, 351, 29);
		panel_TagAddLockAndUnlock.add(panel_advancedOperation);
		panel_advancedOperation.setLayout(new GridLayout(0, 3, 0, 0));
		lblUnlockArea = new JLabel(MainStart.rs.getString("lblLabelUnlockArea"));
		lblUnlockArea.setHorizontalAlignment(SwingConstants.CENTER);
		panel_advancedOperation.add(lblUnlockArea);
		cbbUnlockArea = new JComboBox();
		panel_advancedOperation.add(cbbUnlockArea);
		cbbUnlockArea.setModel(new DefaultComboBoxModel(unlockArea));
		lblUnlockType = new JLabel(MainStart.rs.getString("lblLabelUnlockType"));
		lblUnlockType.setHorizontalAlignment(SwingConstants.CENTER);
		panel_advancedOperation.add(lblUnlockType);
		panel_TagAddLockAndUnlock.add(btnUnlockPerform);
		panel_tagAccess.add(panel_tagOperation);
		panel_tagOperation.setLayout(null);
		panel_tagOperation.add(panel_Designatedarea);
		panel_Designatedarea.setLayout(null);
		lblRWAccessPwd = new JLabel(MainStart.rs.getString("lblLabelAccessPassword"));
		lblRWAccessPwd.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRWAccessPwd.setBounds(4, 168, 101, 20);
		panel_Designatedarea.add(lblRWAccessPwd);
		panel_Designatedarea.add(lblRWData);
		panel_Designatedarea.add(lblRWArea);
		panel_Designatedarea.add(txtRWAccessPwd);
		panel_RWOperationButton = new JPanel();
		panel_RWOperationButton.setBounds(223, 165, 491, 27);
		panel_Designatedarea.add(panel_RWOperationButton);
		panel_RWOperationButton.setLayout(new GridLayout(1, 5, 5, 0));
		btnRWContinuousRead = new JButton(MainStart.rs.getString("strBtnConnRead"));
		panel_RWOperationButton.add(btnRWContinuousRead);
		btnRWStop = new JButton(MainStart.rs.getString("strStopRead"));
		panel_RWOperationButton.add(btnRWStop);
		btnRWReadCard = new JButton(MainStart.rs.getString("btnLabelRWRead"));
		panel_RWOperationButton.add(btnRWReadCard);
		btnRWClear = new JButton(MainStart.rs.getString("btnLabelRWClear"));
		panel_RWOperationButton.add(btnRWClear);
		btnRWWrite = new JButton(MainStart.rs.getString("btnLabelRWWrite"));
		panel_RWOperationButton.add(btnRWWrite);
		panel_RWOperationArea = new JPanel();
		panel_RWOperationArea.setBounds(99, 26, 522, 28);
		panel_Designatedarea.add(panel_RWOperationArea);
		panel_RWOperationArea.setLayout(new GridLayout(0, 5, 0, 0));
		cboRWDesignatedArea = new JComboBox();
		panel_RWOperationArea.add(cboRWDesignatedArea);
		cboRWDesignatedArea.setModel(new DefaultComboBoxModel(new String[] {"Reserve", "EPC", "TID", "User" }));
		lblRWStartAddress = new JLabel(MainStart.rs.getString("LblLabelRWStartAddr"));
		lblRWStartAddress.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RWOperationArea.add(lblRWStartAddress);
		cboRWDesignatedStartAddress = new JComboBox();
		panel_RWOperationArea.add(cboRWDesignatedStartAddress);
		lblRWLength = new JLabel(MainStart.rs.getString("lblLabelRWLen"));
		lblRWLength.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RWOperationArea.add(lblRWLength);
		cboRWDesignatedLength = new JComboBox();
		panel_RWOperationArea.add(cboRWDesignatedLength);
		ReadWriteData.defaultLoadCombobox(cboRWDesignatedArea,cboRWDesignatedStartAddress, cboRWDesignatedLength);
		panel_Designatedarea.add(spRWData);
		panel_tagOperation.add(panel_TagAddLockAndUnlock);
		panel_tagOperation.add(labResult);
		panel_destoryTag = new JPanel();
		panel_destoryTag.setBorder(new TitledBorder(null, MainStart.rs
				.getString("gopLabelDestoryTag"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_destoryTag.setBounds(0, 336, 773, 74);
		panel_tagOperation.add(panel_destoryTag);
		panel_destoryTag.setLayout(null);
		JPanel panel_tagDestory = new JPanel();
		panel_tagDestory.setBounds(354, 22, 361, 30);
		panel_destoryTag.add(panel_tagDestory);
		panel_tagDestory.setLayout(new GridLayout(0, 3, 15, 0));
		lblDestoryPassword = new JLabel(
				MainStart.rs.getString("lblLabelRWDestoryPassword"));
		lblDestoryPassword.setHorizontalAlignment(SwingConstants.CENTER);
		panel_tagDestory.add(lblDestoryPassword);
		txtDestoryPassword = new JTextField();
		panel_tagDestory.add(txtDestoryPassword);
		txtDestoryPassword.setColumns(10);
		btnDestoryTag = new JButton(MainStart.rs.getString("btnDestoryTag"));
		panel_tagDestory.add(btnDestoryTag);
		TagRWOperateOnclick.onclick();
	}

	/**
	 * 通讯参数设置_同分立器一样
	 */
	private void commParamsSetting() {
		String[] ZLcolumnHeader = { MainStart.rs.getString("strZlHeadMAC"), MainStart.rs.getString("strZlHeadPort"), MainStart.rs.getString("strZlHeadIP"),
				MainStart.rs.getString("strLvHeadNo") };
		// 表格模型对象
		ZLtableModel = new DefaultTableModel(null, ZLcolumnHeader);
		panel_commParamsSetting = new JPanel();
		tabbedPane.addTab(MainStart.rs.getString("strTpSetCommParam"), null, panel_commParamsSetting, null);
		panel_communicationParameterSetting = new JPanel();
		panel_communicationParameterSetting.setBounds(51, 24, 763, 428);
		panel_showIPInfo = new JPanel();
		panel_showIPInfo.setBounds(0, 0, 378, 228);
		panel_NetParams = new JPanel();
		panel_NetParams.setBounds(393, 0, 360, 362);
		panel_NetParams.setBorder(new TitledBorder(null, MainStart.rs.getString("strGbNetParams"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_SerialPortParams = new JPanel();
		panel_SerialPortParams.setBounds(3, 307, 370, 111);
		panel_SerialPortParams.setBorder(new TitledBorder(null, MainStart.rs.getString("strGbSPParams"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_searchAndEditDeivce = new JPanel();
		panel_searchAndEditDeivce.setBounds(44, 238, 282, 40);
		panel_networkParamButton = new JPanel();
		panel_networkParamButton.setBounds(468, 380, 250, 35);
		lblPromotion = new JLabel(MainStart.rs.getString("strLabPromotion"));
		lblPromotion.setBounds(42, 253, 226, 15);
		panel_networkParamTitle = new JPanel();
		panel_networkParamTitle.setBounds(42, 27, 102, 217);
		panel_networkParamText = new JPanel();
		panel_networkParamText.setBounds(179, 27, 123, 217);
		panel_clientTitle = new JPanel();
		panel_clientTitle.setBounds(42, 275, 102, 70);
		panel_clientText = new JPanel();
		panel_clientText.setBounds(179, 275, 123, 70);
		panel_clientText.setLayout(new GridLayout(2, 1, 0, 10));
		txtDestinationIP = new JTextField();
		panel_clientText.add(txtDestinationIP);
		txtDestinationIP.setColumns(10);
		txtDestinationPort = new JTextField();
		panel_clientText.add(txtDestinationPort);
		txtDestinationPort.setColumns(10);
		panel_clientTitle.setLayout(null);
		lblDestinationIP = new JLabel(MainStart.rs.getString("strLabDestIP"));
		lblDestinationIP.setBounds(0, 0, 102, 35);
		panel_clientTitle.add(lblDestinationIP);
		lblDestinationPort = new JLabel(MainStart.rs.getString("strLabDestPort"));
		lblDestinationPort.setBounds(0, 35, 102, 35);
		panel_clientTitle.add(lblDestinationPort);
		panel_networkParamText.setLayout(new GridLayout(6, 1, 0, 10));
		cbbNetworkMode = new JComboBox();
		panel_networkParamText.add(cbbNetworkMode);
		cbbNetworkMode.setModel(new DefaultComboBoxModel(new String[] { "TCP Server", "TCP Client", "UDP", "UDP Group" }));
		cbbIPMode = new JComboBox();
		panel_networkParamText.add(cbbIPMode);
		cbbIPMode.setModel(new DefaultComboBoxModel(new String[] { "Static", "Dynamic" }));
		txtIPAddress = new JTextField();
		panel_networkParamText.add(txtIPAddress);
		txtIPAddress.setColumns(10);
		txtSubnetMask = new JTextField();
		txtSubnetMask.setColumns(10);
		panel_networkParamText.add(txtSubnetMask);
		txtPort = new JTextField();
		txtPort.setColumns(10);
		panel_networkParamText.add(txtPort);
		txtGateway = new JTextField();
		txtGateway.setColumns(10);
		panel_networkParamText.add(txtGateway);
		panel_networkParamTitle.setLayout(new GridLayout(6, 1, 0, 0));
		lblNetworkMode = new JLabel(MainStart.rs.getString("strLabNetMode"));
		panel_networkParamTitle.add(lblNetworkMode);
		lblIPMode = new JLabel(MainStart.rs.getString("strLabIPMode"));
		panel_networkParamTitle.add(lblIPMode);
		lblIPAddress = new JLabel(MainStart.rs.getString("strLabIPAdd"));
		panel_networkParamTitle.add(lblIPAddress);
		lblSubnetMask = new JLabel(MainStart.rs.getString("strLabMask"));
		panel_networkParamTitle.add(lblSubnetMask);
		lblPort = new JLabel(MainStart.rs.getString("lblLabelOutPort"));
		panel_networkParamTitle.add(lblPort);
		lblGateway = new JLabel(MainStart.rs.getString("strLabGateway"));
		panel_networkParamTitle.add(lblGateway);
		panel_searchAndEditDeivce.setLayout(new GridLayout(0, 2, 50, 0));
		btnSearchDevice = new JButton(MainStart.rs.getString("strBtnSearchDev"));
		panel_searchAndEditDeivce.add(btnSearchDevice);
		btnEditDevice = new JButton(MainStart.rs.getString("strBtnModifyDev"));
		panel_searchAndEditDeivce.add(btnEditDevice);
		panel_showIPInfo.setLayout(new GridLayout(1, 0, 0, 0));
		sp_showIPInfo = new JScrollPane();
		panel_showIPInfo.add(sp_showIPInfo);
		table_ZL = new JTable(ZLtableModel);
		CommTable.setTableStyleZL(table_ZL);
		table_ZL.setEnabled(false);
		sp_showIPInfo.setViewportView(table_ZL);
		panel_networkParamButton.setLayout(new GridLayout(0, 2, 10, 0));
		btnDefaultParams = new JButton(MainStart.rs.getString("strBtnDefaultParams"));
		panel_networkParamButton.add(btnDefaultParams);
		btnSetParams = new JButton(MainStart.rs.getString("strBtnSetParams"));
		panel_commParamsSetting.setLayout(null);
		panel_networkParamButton.add(btnSetParams);
		panel_communicationParameterSetting.setLayout(null);
		panel_communicationParameterSetting.add(panel_showIPInfo);
		panel_communicationParameterSetting.add(panel_SerialPortParams);
		panel_SerialPortParams.setLayout(null);
		panel_SerialPortSet = new JPanel();
		panel_SerialPortSet.setBounds(18, 62, 156, 30);
		panel_SerialPortParams.add(panel_SerialPortSet);
		panel_SerialPortSet.setLayout(new GridLayout(0, 2, 0, 0));
		lblDataBits = new JLabel(MainStart.rs.getString("strLabDataBits"));
		lblDataBits.setHorizontalAlignment(SwingConstants.CENTER);
		panel_SerialPortSet.add(lblDataBits);
		cboDataBits = new JComboBox();
		panel_SerialPortSet.add(cboDataBits);
		cboDataBits.setModel(new DefaultComboBoxModel(new String[] { "8", "7", "6", "5" }));
		panel_SerialPortParamsSet = new JPanel();
		panel_SerialPortParamsSet.setBounds(17, 20, 316, 31);
		panel_SerialPortParams.add(panel_SerialPortParamsSet);
		panel_SerialPortParamsSet.setLayout(new GridLayout(0, 4, 0, 0));
		lblBaudRate = new JLabel(MainStart.rs.getString("strLabBaudRate"));
		lblBaudRate.setHorizontalAlignment(SwingConstants.CENTER);
		panel_SerialPortParamsSet.add(lblBaudRate);
		cbbSerialPortBaudRate = new JComboBox();
		panel_SerialPortParamsSet.add(cbbSerialPortBaudRate);
		cbbSerialPortBaudRate.setModel(new DefaultComboBoxModel(new String[] { "1200", "2400", "4800", "7200", "9600", "14400",
				"19200", "28800", "38400", "57600", "76800", "115200", "230400" }));
		lblParity = new JLabel(MainStart.rs.getString("strLabCheckBits"));
		lblParity.setHorizontalAlignment(SwingConstants.CENTER);
		panel_SerialPortParamsSet.add(lblParity);
		cbbParity = new JComboBox();
		panel_SerialPortParamsSet.add(cbbParity);
		cbbParity.setModel(new DefaultComboBoxModel(new String[] { "Node", "Odd", "Even", "Marked", "Space" }));
		panel_communicationParameterSetting.add(panel_searchAndEditDeivce);
		panel_communicationParameterSetting.add(panel_NetParams);
		panel_NetParams.setLayout(null);
		panel_NetParams.add(lblPromotion);
		panel_NetParams.add(panel_clientTitle);
		panel_NetParams.add(panel_clientText);
		panel_NetParams.add(panel_networkParamTitle);
		panel_NetParams.add(panel_networkParamText);
		panel_communicationParameterSetting.add(panel_networkParamButton);
		panel_commParamsSetting.add(panel_communicationParameterSetting);
		CommParamsSettingOnclick.onclick();
	}

	/**
	 * 参数设置
	 */
	public void deviceParametersSetup() {
		panel_parametersSetup = new JPanel();
		tabbedPane.addTab(MainStart.rs.getString("tabDeviceParams"), null,panel_parametersSetup, null);
		panel_deviceParamLeft = new JPanel();
		panel_deviceParamLeft.setBounds(45, 1, 367, 474);
		panel_deviceParamRight = new JPanel();
		panel_deviceParamRight.setBounds(422, 0, 479, 476);
		antennaSet();
		buzzerSet();
		setOutPort();
		deviceWork();
		deviceAdjacent();
		deviceNo();
		deviceTimeClock();
		deviceSearchArea();
		deviceOut();
		panel_deviceParamRight.setLayout(null);
		panel_parametersSetup.setLayout(null);
		panel_deviceParamLeft.setLayout(null);
		labelShowInfo = new Label("");
		labelShowInfo.setBounds(0, 446, 123, 23);
		panel_deviceParamLeft.add(labelShowInfo);
		panel_parametersSetup.add(panel_deviceParamLeft);
		panel_parametersSetup.add(panel_deviceParamRight);
		DeviceParamsOnclick.onclick();
	}

	public void deviceOut() {
		panel_DeviceOut = new JPanel();
		panel_DeviceOut.setBounds(-1, 355, 452, 85);
		panel_DeviceOut.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				MainStart.rs.getString("gopDeviceOut"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		cboDeviceOut = new JComboBox();
		String[] deviceOut = { MainStart.rs.getString("cboserialport"),
				MainStart.rs.getString("rdobtnNet"), "RS485", "WIFI" };
		cboDeviceOut.setModel(new DefaultComboBoxModel(deviceOut));
		cboDeviceOut.setBounds(16, 28, 125, 28);
		panel_DeviceOut.setLayout(null);
		panel_DeviceOut.add(cboDeviceOut);
		panel_setOut = new JPanel();
		panel_setOut.setBounds(230, 26, 164, 31);
		panel_DeviceOut.add(panel_setOut);
		panel_setOut.setLayout(new GridLayout(0, 2, 0, 0));
		btnDeviceOutSet = new JButton(MainStart.rs.getString("btnDeviceSet"));
		panel_setOut.add(btnDeviceOutSet);
		btnDeviceOutRead = new JButton(MainStart.rs.getString("btnDeviceOutRead"));
		panel_setOut.add(btnDeviceOutRead);
		panel_deviceParamRight.add(panel_DeviceOut);
	}

	/**
	 * 寻卡区域
	 */
	public void deviceSearchArea() {
		panel_DeviceSearch = new JPanel();
		panel_DeviceSearch.setBounds(0, 218, 451, 129);
		panel_DeviceSearch.setBorder(
		new TitledBorder(null, MainStart.rs.getString("gopDeviceSearch"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_readAreaContent = new JPanel();
		panel_readAreaContent.setBounds(91, 15, 345, 95);
		rdoDeviceSearchCustom = new JRadioButton(
				MainStart.rs.getString("rdobtnDeviceSearchCustom"));
		rdoDeviceSearchCustom.setBounds(16, 51, 77, 23);
		panel_readAreaContent.setLayout(new GridLayout(3, 3, 10, 2));
		lblDeviceSearchArea = new JLabel(MainStart.rs.getString("lblDeviceSearchArea"));
		lblDeviceSearchArea.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeviceSearchArea.setOpaque(true);
		panel_readAreaContent.add(lblDeviceSearchArea);
		lblDeviceSearchStartAddr = new JLabel(
				MainStart.rs.getString("lblDeviceSearchStartAddr"));
		lblDeviceSearchStartAddr.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeviceSearchStartAddr.setOpaque(true);
		panel_readAreaContent.add(lblDeviceSearchStartAddr);
		lblDeviceSearchLen = new JLabel(MainStart.rs.getString("lblDeviceSearchLen"));
		lblDeviceSearchLen.setHorizontalAlignment(SwingConstants.CENTER);
		panel_readAreaContent.add(lblDeviceSearchLen);
		cboDeviceSearchArea = new JComboBox();
		String[] deviceSearchArea = { "EPC", "TID",
				MainStart.rs.getString("cbousersarea") };
		cboDeviceSearchArea
				.setModel(new DefaultComboBoxModel(deviceSearchArea));
		panel_readAreaContent.add(cboDeviceSearchArea);
		cboDeviceSearchStartAddr = new JComboBox();
		for (int i = 0; i < 32; i++) {
			cboDeviceSearchStartAddr.addItem(i);
		}
		panel_readAreaContent.add(cboDeviceSearchStartAddr);
		cboDeviceSearchLen = new JComboBox();
		for (int i = 0; i < 32; i++) {
			cboDeviceSearchLen.addItem(i + 1);
		}
		panel_readAreaContent.add(cboDeviceSearchLen);
		lbl_temp = new JLabel("");
		panel_readAreaContent.add(lbl_temp);
		btnDeviceSearchSet = new JButton(MainStart.rs.getString("btnDeviceSearchSet"));
		panel_readAreaContent.add(btnDeviceSearchSet);
		btnDeviceSearchRead = new JButton(MainStart.rs.getString("btnDeviceAdjacentRead"));
		panel_readAreaContent.add(btnDeviceSearchRead);
		panel_DeviceSearch.setLayout(null);
		rdoDeviceSearchEPC = new JRadioButton(MainStart.rs.getString("rdobtnDeviceSearchEPC"));
		ButtonGroup bgDeviceSet = new ButtonGroup();
		bgDeviceSet.add(rdoDeviceSearchCustom);
		bgDeviceSet.add(rdoDeviceSearchEPC);
		rdoDeviceSearchEPC.setBounds(15, 82, 160, 23);
		panel_deviceParamRight.add(panel_DeviceSearch);
		panel_DeviceSearch.add(rdoDeviceSearchEPC);
		panel_DeviceSearch.add(rdoDeviceSearchCustom);
		panel_DeviceSearch.add(panel_readAreaContent);
	}

	/**
	 * 时钟设置
	 */
	public void deviceTimeClock() {
		panel_DeviceTime = new JPanel();
		datepick = new DatePicker(this, new Date());// c为此组件所许要放置的父容器
		datepick.setSize(160, 29);
		datepick.setLocation(24, 26);
		panel_DeviceTime.add(datepick);
		datepick.setLocale(Locale.CHINA);// 设置显示语言
		datepick.setTimePanleVisible(true);
		datepick.setPattern("yyyy-MM-dd    HH:mm:ss");// 设置日期格式化字符串
		datepick.setEditorable(false);// 设置是否可编辑
		datepick.setBackground(Color.gray);// 设置背景色
		datepick.setForeground(Color.yellow);// 设置前景色
		datepick.setPreferredSize(new Dimension(100, 21));// 设置大小
		datepick.setTextAlign(DatePicker.CENTER);// 设置文本水平方向位置：DatePicker.CENTER
													// 水平居中，DatePicker.LEFT 水平靠左
													// DatePicker.RIGHT 水平靠右
		datepick.setEnabled(true);
		panel_DeviceTime.setBounds(0, 127, 453, 89);
		panel_DeviceTime.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), MainStart.rs
				.getString("gopDeviceTime"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));

		btnDeviceCurrTime = new JButton(MainStart.rs.getString("btnDeviceCurrTime"));
		btnDeviceCurrTime.setBounds(243, 31, 98, 26);
		panel_timer = new JPanel();
		panel_timer.setBounds(358, 17, 77, 52);
		panel_DeviceTime.add(panel_timer);
		panel_timer.setLayout(new GridLayout(0, 1, 0, 0));

		btnDeviceTimeSet = new JButton(MainStart.rs.getString("btnDeviceTimeSet"));
		panel_timer.add(btnDeviceTimeSet);

		btnDeviceTimeRead = new JButton(MainStart.rs.getString("btnDeviceTimeRead"));
		panel_timer.add(btnDeviceTimeRead);
	
		panel_deviceParamRight.add(panel_DeviceTime);
		panel_DeviceTime.setLayout(null);
		panel_DeviceTime.add(btnDeviceCurrTime);
	}

	/**
	 * 设置设备号
	 */
	public void deviceNo() {
		panel_Device = new JPanel();
		panel_Device.setBounds(0, 69, 452, 58);
		panel_Device.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), MainStart.rs.getString("gopDevice"),
				TitledBorder.LEADING, TitledBorder.TOP, null,new Color(0, 0, 0)));
		lblDeviceValid = new JLabel(MainStart.rs.getString("lblDeviceValid"));
		lblDeviceValid.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDeviceValid.setBounds(37, 24, 114, 23);
		panel_DeviceNo = new JPanel();
		panel_DeviceNo.setBounds(184, 24, 251, 23);
		panel_Device.add(panel_DeviceNo);
		panel_DeviceNo.setLayout(null);

		txtDeviceValid = new JTextField();
		txtDeviceValid.setBounds(0, 0, 77, 23);
		panel_DeviceNo.add(txtDeviceValid);
		txtDeviceValid.setColumns(10);

		btnDeviceSet = new JButton(MainStart.rs.getString("btnDeviceSet"));
		btnDeviceSet.setBounds(87, 0, 77, 23);
		panel_DeviceNo.add(btnDeviceSet);

		btnDeviceRead = new JButton(MainStart.rs.getString("btnDeviceRead"));
		btnDeviceRead.setBounds(174, 0, 77, 23);
		panel_DeviceNo.add(btnDeviceRead);
		panel_Device.setLayout(null);
		panel_Device.add(lblDeviceValid);
		panel_deviceParamRight.add(panel_Device);
	}

	/**
	 * 相邻判别
	 */
	private void deviceAdjacent() {
		panel_DeviceAdjacent = new JPanel();
		panel_DeviceAdjacent.setBounds(0, 2, 452, 68);
		panel_DeviceAdjacent.setBorder(new TitledBorder(null, MainStart.rs.getString("gopDeviceAdjacent"), TitledBorder.LEADING,TitledBorder.TOP, null, null));
		lblDeviceAdjacentTime = new JLabel(
				MainStart.rs.getString("lblDeviceAdjacentTime"));
		lblDeviceAdjacentTime.setBounds(24, 19, 153, 22);
		panel_DeviceAdjacent.setLayout(null);
		panel_DeviceAdjacent.add(lblDeviceAdjacentTime);

		panel_DataFiltering = new JPanel();
		panel_DataFiltering.setBounds(185, 18, 251, 23);
		panel_DeviceAdjacent.add(panel_DataFiltering);
		panel_DataFiltering.setLayout(null);

		txtDeviceAdjacentTime = new JTextField();
		txtDeviceAdjacentTime.setBounds(0, 0, 77, 23);
		panel_DataFiltering.add(txtDeviceAdjacentTime);
		txtDeviceAdjacentTime.setColumns(10);

		btnDeviceAdjacentSet = new JButton(MainStart.rs.getString("btnDeviceAdjacentSet"));
		btnDeviceAdjacentSet.setBounds(87, 0, 77, 23);
		panel_DataFiltering.add(btnDeviceAdjacentSet);

		btnDeviceAdjacentRead = new JButton(
				MainStart.rs.getString("btnDeviceAdjacentRead"));
		btnDeviceAdjacentRead.setBounds(174, 0, 77, 23);
		panel_DataFiltering.add(btnDeviceAdjacentRead);

		panel_deviceParamRight.add(panel_DeviceAdjacent);
	}

	/**
	 * 设置工作模式
	 */
	public void deviceWork() {
		panel_DeviceWork = new JPanel();
		panel_DeviceWork.setBounds(0, 352, 357, 88);
		panel_DeviceWork.setBorder(new TitledBorder(null, MainStart.rs
				.getString("gopDeviceWork"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_workMode = new JPanel();
		panel_DeviceWork.add(panel_workMode);
		panel_DeviceWork.setLayout(new GridLayout(0, 1, 0, 0));
		panel_workMode.setLayout(new GridLayout(2, 3, 10, 0));

		lbltemp = new JLabel("");
		panel_workMode.add(lbltemp);

		cboDeviceWorkMode = new JComboBox();
		
		String[] deviceWorkMode = { MainStart.rs.getString("cboAmasterslavemode"),
				MainStart.rs.getString("cboTimingmode"), MainStart.rs.getString("cboTriggermode") };
		cboDeviceWorkMode.setModel(new DefaultComboBoxModel(deviceWorkMode));
		panel_workMode.add(cboDeviceWorkMode);
		btnDeviceWorkSet = new JButton(MainStart.rs.getString("btnDeviceWorkSet"));
		panel_workMode.add(btnDeviceWorkSet);
		lblDeviceWorkDelay = new JLabel(MainStart.rs.getString("lblDeviceWorkDelay"));
		lblDeviceWorkDelay.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDeviceWorkDelay.setVisible(false);
		panel_workMode.add(lblDeviceWorkDelay);
		txtDeviceWorkDelay = new JTextField();
		txtDeviceWorkDelay.setVisible(false);
		panel_workMode.add(txtDeviceWorkDelay);
		txtDeviceWorkDelay.setColumns(10);
		btnDeviceWorkRead = new JButton(MainStart.rs.getString("btnDeviceWorkRead"));
		panel_workMode.add(btnDeviceWorkRead);
		panel_deviceParamLeft.add(panel_DeviceWork);
	}

	/**
	 * 输出口设置
	 */
	public void setOutPort() {
		panel_SetOutPort = new JPanel();
		panel_SetOutPort.setBounds(0, 275, 357, 71);

		panel_SetOutPort.setBorder(new TitledBorder(null, MainStart.rs
				.getString("gopLabelSetOutPort"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		lblOutPort = new JLabel(MainStart.rs.getString("lblLabelOutPort"));
		lblOutPort.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOutPort.setBounds(10, 20, 58, 15);

		cboOutPort = new JComboBox();
		cboOutPort.setModel(new DefaultComboBoxModel(new String[] { "1", "2" }));
		cboOutPort.setBounds(78, 17, 88, 21);
		panel_setOutPortOpenAndClose = new JPanel();
		panel_setOutPortOpenAndClose.setBounds(78, 42, 131, 19);
		panel_SetOutPort.add(panel_setOutPortOpenAndClose);
		panel_setOutPortOpenAndClose.setLayout(new GridLayout(1, 0, 0, 0));

		rdoOutOpen = new JRadioButton(MainStart.rs.getString("rdobtnLabelOutOpen"));
		panel_setOutPortOpenAndClose.add(rdoOutOpen);

		rdoOutClose = new JRadioButton(MainStart.rs.getString("rdobtnLabelOutClose"));
		panel_setOutPortOpenAndClose.add(rdoOutClose);

		btnLabelOutSet = new JButton(MainStart.rs.getString("btnLabelOutSet"));
		ButtonGroup bgSetPortOut = new ButtonGroup();
		rdoOutOpen.setSelected(true);
		bgSetPortOut.add(rdoOutOpen);
		bgSetPortOut.add(rdoOutClose);
		btnLabelOutSet.setBounds(237, 22, 82, 31);
		panel_SetOutPort.setLayout(null);
		panel_SetOutPort.add(lblOutPort);
		panel_SetOutPort.add(cboOutPort);
		panel_SetOutPort.add(btnLabelOutSet);
		panel_deviceParamLeft.add(panel_SetOutPort);
	}

	/**
	 * 声音设置
	 */
	public void buzzerSet() {
		panel_DeviceBuzzer = new JPanel();
		panel_DeviceBuzzer.setBounds(0, 221, 357, 51);
		panel_DeviceBuzzer.setBorder(new TitledBorder(null, MainStart.rs
				.getString("gopDeviceBuzzer"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_buzzSetContent = new JPanel();
		panel_buzzSetContent.setBounds(16, 17, 325, 25);
		panel_buzzSetContent.setLayout(new GridLayout(1, 0, 10, 0));

		rdoDeviceBuzzerOpen = new JRadioButton(
				MainStart.rs.getString("rdobtnDeviceBuzzerOpen"));
		panel_buzzSetContent.add(rdoDeviceBuzzerOpen);

		rdoDeviceBuzzerClose = new JRadioButton(
				MainStart.rs.getString("rdobtnDeviceBuzzerClose"));
		panel_buzzSetContent.add(rdoDeviceBuzzerClose);

		ButtonGroup bgBuzz = new ButtonGroup();
		
		bgBuzz.add(rdoDeviceBuzzerOpen);
		bgBuzz.add(rdoDeviceBuzzerClose);

		btnDeviceBuzzerRead = new JButton(MainStart.rs.getString("btnDeviceBuzzerRead"));
		panel_buzzSetContent.add(btnDeviceBuzzerRead);

		btnDeviceBuzzerSet = new JButton(MainStart.rs.getString("btnDeviceBuzzerSet"));
		panel_buzzSetContent.add(btnDeviceBuzzerSet);
		panel_deviceParamLeft.add(panel_DeviceBuzzer);
		panel_DeviceBuzzer.setLayout(null);
		panel_DeviceBuzzer.add(panel_buzzSetContent);
	}

	/**
	 * 天线参数
	 */
	public void antennaSet() {
		panel_DeviceAntenna = new JPanel();
		panel_DeviceAntenna.setBounds(0, 12, 357, 205);
		panel_DeviceAntenna.setBorder(new TitledBorder(null,MainStart.rs.getString("gopDeviceAntenna"), TitledBorder.LEADING,TitledBorder.TOP, null, null));
		panel_antennaParamsContent = new JPanel();
		panel_antennaParamsContent.setBounds(21, 38, 292, 116);
		lblDeviceAntennaWorkTime = new JLabel(
				MainStart.rs.getString("lblDeviceAntennaWorkTime"));
		lblDeviceAntennaWorkTime.setBounds(132, 17, 97, 15);
		lblDeviceAntennaPower = new JLabel(
				MainStart.rs.getString("lblDeviceAntennaPower"));
		lblDeviceAntennaPower.setBounds(239, 17, 89, 15);
		panel_antennaParamsButton = new JPanel();
		panel_antennaParamsButton.setBounds(122, 164, 191, 30);
		panel_antennaParamsButton.setLayout(new GridLayout(1, 0, 30, 1));

		btnDeviceAntennaRead = new JButton(MainStart.rs.getString("btnDeviceAntennaRead"));
		panel_antennaParamsButton.add(btnDeviceAntennaRead);

		btnDeviceAntannaSet = new JButton(MainStart.rs.getString("btnDeviceAntannaSet"));
		panel_antennaParamsButton.add(btnDeviceAntannaSet);
		panel_antennaParamsContent.setLayout(new GridLayout(0, 3, 10, 2));

		chkDeviceAntenna1 = new JCheckBox(MainStart.rs.getString("chkDeviceAntenna1"));
		chkDeviceAntenna1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_antennaParamsContent.add(chkDeviceAntenna1);

		cboDeviceAntennaWorkTime1 = new JComboBox();
		panel_antennaParamsContent.add(cboDeviceAntennaWorkTime1);

		cboDeviceAntennaPower1 = new JComboBox();
		panel_antennaParamsContent.add(cboDeviceAntennaPower1);
		chkDeviceAntenna2 = new JCheckBox(MainStart.rs.getString("chkDeviceAntenna2"));
		chkDeviceAntenna2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_antennaParamsContent.add(chkDeviceAntenna2);

		cboDeviceAntennaWorkTime2 = new JComboBox();
		panel_antennaParamsContent.add(cboDeviceAntennaWorkTime2);

		cboDeviceAntennaPower2 = new JComboBox();
		panel_antennaParamsContent.add(cboDeviceAntennaPower2);
		chkDeviceAntenna3 = new JCheckBox(MainStart.rs.getString("chkDeviceAntenna3"));
		chkDeviceAntenna3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_antennaParamsContent.add(chkDeviceAntenna3);

		cboDeviceAntennaWorkTime3 = new JComboBox();
		panel_antennaParamsContent.add(cboDeviceAntennaWorkTime3);

		cboDeviceAntennaPower3 = new JComboBox();
		panel_antennaParamsContent.add(cboDeviceAntennaPower3);

		chkDeviceAntenna4 = new JCheckBox(MainStart.rs.getString("chkDeviceAntenna4"));
		chkDeviceAntenna4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_antennaParamsContent.add(chkDeviceAntenna4);
		cboDeviceAntennaWorkTime4 = new JComboBox();
		panel_antennaParamsContent.add(cboDeviceAntennaWorkTime4);
		cboDeviceAntennaPower4 = new JComboBox();
		panel_antennaParamsContent.add(cboDeviceAntennaPower4);
		panel_DeviceAntenna.setLayout(null);
		panel_DeviceAntenna.add(lblDeviceAntennaWorkTime);
		panel_DeviceAntenna.add(lblDeviceAntennaPower);
		panel_DeviceAntenna.add(panel_antennaParamsButton);
		panel_DeviceAntenna.add(panel_antennaParamsContent);
		panel_deviceParamLeft.add(panel_DeviceAntenna);
		
		AntennaParameter.comboboxSecond(cboDeviceAntennaWorkTime1);
		AntennaParameter.comboboxSecond(cboDeviceAntennaWorkTime2);
		AntennaParameter.comboboxSecond(cboDeviceAntennaWorkTime3);
		AntennaParameter.comboboxSecond(cboDeviceAntennaWorkTime4);
		
		AntennaParameter.comboboxDbm(cboDeviceAntennaPower1);
		AntennaParameter.comboboxDbm(cboDeviceAntennaPower2);
		AntennaParameter.comboboxDbm(cboDeviceAntennaPower3);
		AntennaParameter.comboboxDbm(cboDeviceAntennaPower4);
	}

	public static String cardAddEmpity(String cardNo) {
		char[] ch = cardNo.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			sb.append(ch[i]);
			if ((i + 1) % 2 == 0 && i < 24) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	// The size port of the convert
	public static int ReverseByte(int value) {
		return ((value & 0xFF) << 8 | (value & 0xFF00) >> 8);
	}
}
