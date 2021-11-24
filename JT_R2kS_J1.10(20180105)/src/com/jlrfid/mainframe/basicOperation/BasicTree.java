package com.jlrfid.mainframe.basicOperation;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.jlrfid.mainframe.tool.R2kUtil;
import com.jlrfid.r2ks.GetSerialPort;

public class BasicTree {
	/**
	 * ����
	 */
	@SuppressWarnings("unused")
	public static void createTree(JTree tree,DefaultTreeModel model) {
		tree = new JTree(model);
		// ��ʾ�����豸
		tree.setRootVisible(true);
		// ��ʾ������ͷ
		tree.setShowsRootHandles(true);
		// tree_onlineDevice.expandPath(new TreePath(node_1.getPath()));
		DefaultTreeCellRenderer render = (DefaultTreeCellRenderer) tree.getCellRenderer();
		// render.setLeafIcon(null);
		// render.setClosedIcon(null);
		// render.setOpenIcon(null);
		// ����Tree��ѡ��Ϊһ��ֻ��ѡ��һ���ڵ��ѡ��
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		// TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION ����Tree��ѡ��Ϊһ��ѡ�����ڵ�
		// tree_onlineDevice.putClientProperty("JTree.lineStyle",Boolean.TRUE);
	}
	
	public static void getDeviceIP(DefaultTreeModel model,JTree tree,DefaultMutableTreeNode[] nodeTree, DefaultMutableTreeNode node) {
		node.removeAllChildren();
		if (R2kUtil.secondhandler.dllInit("dll/ZLDevManage")) {
			// get network IP
			int devCnt = R2kUtil.secondhandler.startSearchDev();
			// String [] IPAddress = new String[devCnt];
			nodeTree = new DefaultMutableTreeNode[devCnt];
			for (byte i = 0; i < devCnt; ++i) {
				String IP = R2kUtil.secondhandler.getIP(i).toString();
				// IPAddress[i] = IP;
				nodeTree[i] = new DefaultMutableTreeNode(IP);
			}
			int length = nodeTree.length;
			for (int i = 0; i < length; i++) {
				node.add(nodeTree[i]);
			}
			if (devCnt > 0) {
				// Basic.cbbNetwork.setSelectedIndex(0);
			}
		}
	}

	/***
	 * ˢ��������������ԭ����չ��״̬
	 */
	public static void updateTree(DefaultMutableTreeNode[] nodeTree, DefaultTreeModel model, JTree tree,
			DefaultMutableTreeNode node_1) {
		Vector<TreePath> v = new Vector<TreePath>();
		BasicTree.getExpandNode(model, tree, node_1, v);
		getDeviceIP(model,tree,nodeTree, node_1);
		node_1.removeAllChildren();
		BasicTree.addNode(nodeTree, node_1, nodeTree.length);
		model.reload();
	}

	/**
	 * ��ȫչ��һ������ر�һ����
	 * 
	 * @param tree
	 *            JTree
	 * @param parent
	 *            ���ڵ�
	 * @param expand
	 *            true ��ʾչ����false ��ʾ�ر�
	 */
	@SuppressWarnings("rawtypes")
	public static void expandAll(JTree tree, TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();

		if (node.getChildCount() > 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}

	@SuppressWarnings("rawtypes")
	public static Vector<TreePath> getExpandNode(DefaultTreeModel model, JTree tree, TreeNode node,
			Vector<TreePath> v) {
		if (node.getChildCount() > 0) {
			TreePath treePath = new TreePath(model.getPathToRoot(node));
			if (tree.isExpanded(treePath))
				v.add(treePath);
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				getExpandNode(model, tree, n, v);
			}
		}
		return v;
	}

	public static void addNode(DefaultMutableTreeNode[] nodeTree, DefaultMutableTreeNode node, int n) {
		for (int i = 0; i < nodeTree.length; i++) {
			node.add(nodeTree[i]);
		}
	}

	/**
	 * 
	 * @param myTree
	 *            ��
	 * @param currNode
	 *            չ���ڵ�ĸ��ڵ�
	 * @param vNode
	 *            չ���ڵ㣬·���ַ���|·��Node��ɵ�Vector�����Ӹ��ڵ㿪ʼ��������ӵ�Vector
	 */
	public static void expandNode(JTree myTree, DefaultMutableTreeNode currNode, Vector<Object> vNode) {
		if (currNode.getParent() == null) {
			vNode.removeElementAt(0);
		}
		if (vNode.size() <= 0)
			return;

		int childCount = currNode.getChildCount();
		String strNode = vNode.elementAt(0).toString();
		DefaultMutableTreeNode child = null;
		boolean flag = false;
		for (int i = 0; i < childCount; i++) {
			child = (DefaultMutableTreeNode) currNode.getChildAt(i);
			if (strNode.equals(child.toString())) {
				flag = true;
				break;
			}
		}
		if (child != null && flag) {
			vNode.removeElementAt(0);
			if (vNode.size() > 0) {
				expandNode(myTree, child, vNode);
			} else {
				myTree.expandPath(new TreePath(child.getPath()));
			}
		}
	}

	
	@SuppressWarnings("rawtypes")
	public static void getDeviceComm(DefaultTreeModel model,JTree tree,DefaultMutableTreeNode[] nodeTree, DefaultMutableTreeNode node) {
		node.removeAllChildren();
		List devCnt = GetSerialPort.getComm();
		if (devCnt.size() < 1) {
			return;
		}
		nodeTree = new DefaultMutableTreeNode[devCnt.size()];
		for (byte i = 0; i < devCnt.size(); ++i) {
			String comm = devCnt.get(i).toString();
			nodeTree[i] = new DefaultMutableTreeNode(comm);
		}
		int length = nodeTree.length;
		for (int i = 0; i < length; i++) {
			node.add(nodeTree[i]);
		}
		if (devCnt.size() > 0) {
			// Basic.cbbNetwork.setSelectedIndex(0);
		}
		//Ч��һ��
		// TreeNode root = (TreeNode) tree.getModel().getRoot();
		// model.reload(root);
		tree.updateUI();
	}
}
