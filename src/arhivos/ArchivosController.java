/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arhivos;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Sebastian Quintero
 */
public class ArchivosController {

    private HashMap<String, DefaultMutableTreeNode> dictionary;
    private DefaultTreeModel arbol;
    private DefaultMutableTreeNode nodoHijo;

    public ArchivosController() {
        this.nodoHijo = new DefaultMutableTreeNode("C:");
        this.arbol = new DefaultTreeModel(this.nodoHijo);
        this.dictionary = new HashMap<>();
        dictionary.put("C:", this.nodoHijo);
    }

    public HashMap<String, DefaultMutableTreeNode> getDictionary() {
        return dictionary;
    }

    public void setDictionary(HashMap<String, DefaultMutableTreeNode> dictionary) {
        this.dictionary = dictionary;
    }

    public DefaultTreeModel getArbol() {
        return arbol;
    }

    public void setArbol(DefaultTreeModel arbol) {
        this.arbol = arbol;
    }

    public DefaultMutableTreeNode getNodoHijo() {
        return nodoHijo;
    }

    public void setNodoHijo(DefaultMutableTreeNode nodoHijo) {
        this.nodoHijo = nodoHijo;
    }

    public void insertarNodo(String padre, String nodoHijo) {
        //Valida que el padre exista
        if (dictionary.containsKey(padre)) {
            DefaultMutableTreeNode nodoPadre = dictionary.get(padre);
            if (!dictionary.containsKey(nodoHijo)) {
                this.nodoHijo = new DefaultMutableTreeNode(nodoHijo);
                dictionary.put(nodoHijo, this.nodoHijo);
                this.arbol.insertNodeInto(this.nodoHijo, nodoPadre, nodoPadre.getChildCount());
            }
        }
    }
    
    public void eliminarNodo(String nodo, DefaultMutableTreeNode eliminar) {

        this.nodoHijo = new DefaultMutableTreeNode(nodo);
        DefaultMutableTreeNode nodoEliminar = dictionary.get(nodo);
        dictionary.remove(nodo, nodoEliminar);
//        this.arbol.removeNodeFromParent(eliminar);
        this.arbol.removeNodeFromParent(eliminar);

    }

    public void modificar(String old, DefaultMutableTreeNode newN, String newNo) {
        DefaultMutableTreeNode oldNode = dictionary.get(old);
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newNo);
        dictionary.remove(old, oldNode);
        dictionary.put(newNo, newNode);
        oldNode.setUserObject(newNo);
        this.arbol.nodeChanged(oldNode);

    }

    public void guardar() {
        String cadena = "";
        cadena = contar(dictionary.get("C:"), cadena, 0);
        System.out.println(cadena);

        try {
            JFileChooser mFile = new JFileChooser();
            mFile.showOpenDialog(null);
            //abrimos el archivo seleccionado
            File archGrahp = mFile.getSelectedFile();
            FileWriter write = new FileWriter(archGrahp, false);//false para sobrescribir el archivo
            write.write(cadena);
            write.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public String contar(DefaultMutableTreeNode nodo, String cadena, int pos) {
        pos = 0;
        if (nodo.getChildCount() > 0) {
            int cantidadHijos = nodo.getChildCount();
            cadena += nodo.getUserObject() + ";";
            while (cantidadHijos > 0) {

                cadena = contar((DefaultMutableTreeNode) nodo.getChildAt(pos), cadena, pos);
                cantidadHijos--;
                pos++;
            }
        } else {
            cadena += nodo.getUserObject() + "-";
        }
        return cadena;
    }

}
