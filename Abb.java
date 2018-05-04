package Arboles;

import java.util.ArrayList;
import java.util.List;

public class Abb{

	private class NodoArbol {

		private Comparable info;
		private NodoArbol left;
		private NodoArbol right;

		private NodoArbol(Comparable o) {
			info=o;
			left=null;
			right=null;
		}

		private NodoArbol() {
			info=null;
			left=null;
			right=null;
		}

		protected Comparable getInfo() {
			return this.info;
		}

		protected NodoArbol getLeft() {
			return this.left;
		}

		protected NodoArbol getRight() {
			return this.right;
		}

		protected void setLeft(NodoArbol left) {
			this.left=left;
		}

		protected void setRight(NodoArbol right) {
			this.right=right;
		}

		protected void setValor(Comparable info) {
			this.info=info;
		}

		protected boolean isLeaf() {
			if((this.getLeft()==null)&&(this.getRight()==null)) return true;
			else return false;
		}

	}

	private NodoArbol root;
	private int height;

	public Abb(Comparable o) {
		this.root= new NodoArbol(o);
	}

	public Abb() {
		this.root=null;
	}

	public boolean isEmpty() {
		return (root==null);
	}

	public Comparable getRoot() {
		if (this.isEmpty()) {
			return null;
		}
		else return this.root.getInfo();
	}

	public List<NodoArbol>  getLeaves(){
		List leaves= new ArrayList<NodoArbol>();
		getLeaves(leaves, this.root);
		return leaves;
	}

	private void getLeaves(List leaves, NodoArbol node) {
		if(node.isLeaf()) {
			leaves.add(node);
		}
		else {
			if(node.getRight()!=null) getLeaves(leaves, node.getRight());
			if(node.getLeft()!=null) getLeaves(leaves, node.getLeft());
		}
	}

	//En general su complejidad es de O (h) depende de su altura, si el árbol es completo su altura es proporcional al logaritmo del número de nodos: h E O(log n ).
	//En el peor de los casos que se encuentre totalmente desbalanceado al punto que sea una lista la complejidad es de O(n)
	public boolean hasElem(Comparable o) {
		if(!this.isEmpty()) return hasElem(this.root, o);
		else return false;
	}

	private boolean hasElem(NodoArbol rootAux, Comparable o) {
		if (rootAux!=null) {
			int compare= rootAux.getInfo().compareTo(o);
			if(compare==0) return true;
			else if (compare<0) return hasElem(rootAux.getRight(), o);
			else return hasElem(rootAux.getLeft(), o);
		}
		else return false;
	}

	//Su complejidad es de O (h) en general y en el peor de los casos es de O (n)... Es decir en el peor de los casos la altura es igual a la cantidad de nodos
	public void insert(Comparable o) {
		if(this.isEmpty()) {
			this.root= new NodoArbol(o);
		}
		else this.insert(o, this.root);
	}

	private void insert(Comparable o, NodoArbol nodo) {

		if(nodo.getInfo().compareTo(o)>0) {

			if(nodo.getLeft()!=null){
				insert(o, nodo.getLeft());
			}
			else {
				nodo.setLeft(new NodoArbol(o));
			} 
		}
		else if (nodo.getInfo().compareTo(o)<0) {
			if(nodo.getRight()!=null) {
				insert(o, nodo.getRight());
			}
			else {
				nodo.setRight(new NodoArbol(o));
			}
		}
	}

	public int getHeight() {
		this.height=0;
		getHeight(this.root, 1);
		return height;		
	}

	private void getHeight(NodoArbol node, int level) {
		if (node!=null) {
			getHeight(node.getLeft(), level+1);
			if (level>this.height) height=level;
			getHeight(node.getRight(), level+1);
		}
	}

	public List<NodoArbol> getLongestBranch(){
		List<NodoArbol> branch= new ArrayList<NodoArbol>();
		Abb rightSubTree= new Abb(this.root.getRight().getInfo());
		Abb leftSubTree= new Abb(this.root.getLeft().getInfo());
		int right= rightSubTree.getHeight();
		int left= leftSubTree.getHeight();
		if (left> right) {
			getLongestBranch(branch, leftSubTree.root);
		}
		else if(right>left) {
			getLongestBranch(branch, leftSubTree.root);
		}
		return branch;
	}

	private void getLongestBranch(List<NodoArbol> branch, NodoArbol node) {
		if (node!=null){ 
			branch.add(node);
			getLongestBranch(branch, node.left);
			getLongestBranch(branch, node.right);
		}
	}

	public void delete(Comparable o) {
		this.root = delete(o, root);
	}

	private NodoArbol delete(Comparable o, NodoArbol nodo) {

		if(nodo==null) return nodo;

		if (nodo.getInfo().compareTo(o)>0) {
			nodo.setLeft(delete(o, nodo.getLeft()));
		}	
		else if(nodo.getInfo().compareTo(o)<0) {
			nodo.setRight(delete(o, nodo.getRight()));
		}
		else if((nodo.getLeft()!=null)&&(nodo.getRight()!=null)) {
			nodo.setValor(searchMin(nodo.getRight()).getInfo());
			nodo.setRight(delete(nodo.getInfo(), nodo.getRight()));
		}
		else nodo= (nodo.getLeft()!=null) ? nodo.getLeft() : nodo.getRight();

		return nodo;
	}

	private NodoArbol searchMin(NodoArbol nodo) {
		if(nodo == null)
			return null;
		else if(nodo.getLeft() == null)
			return nodo;
		return searchMin(nodo.getLeft());
	}

	public Comparable searchMax() {
		NodoArbol n= searchMax(this.root);
		return (n==null)? null : n.getInfo();
	}

	private NodoArbol searchMax(NodoArbol nodo) {
		if(nodo==null) {
			while(nodo.getRight()!=null) {
				nodo= nodo.getRight();
			}
		}
		return nodo;
	}

	public void printPosOrder() {
		printPosOrder(this.root);
	}

	private void printPosOrder(NodoArbol root) {
		if(root != null) {
			printPosOrder(root.getLeft());
			printPosOrder(root.getRight());
			System.out.print(root.getInfo() + " ");
		}
		else {
			System.out.print("- ");
		}
	}

	public void printPreOrder() {
		printPreOrder(this.root);
	}

	private void printPreOrder(NodoArbol root) {
		if(root != null) {
			System.out.print(root.getInfo() + "  ");
			printPreOrder(root.getLeft());
			printPreOrder(root.getRight());
		}
		else {
			System.out.print(" - ");
		}
	}  

	public void printInOrder() {
		printInOrder(this.root);
	} 

	private void printInOrder(NodoArbol root) {
		if(root != null) {
			printPreOrder(root.getLeft());
			System.out.print(root.getInfo() + " ");
			printPreOrder(root.getRight());
		}
		else {
			System.out.print("- ");
		}
	}





}
