package com.skripsi.lamp.model;

/**
 * Created by gandhi on 1/6/17.
 */

public class MenuModel {
    private String namaMenu;
    private String statusMenu;
    private String idMenu;
    private int icon;

    public MenuModel(String namaMenu, int icon,String statusMenu, String idMenu){
        this.namaMenu = namaMenu;
        this.statusMenu = statusMenu;
        this.idMenu = idMenu;
        this.icon = icon;
    }

    public MenuModel() {

    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getIdMenu() { return idMenu;}

    public void setIdMenu(String idMenu) { this.idMenu = idMenu;}

    public String getStatusMenu() { return statusMenu;}

    public void setStatusMenu(String statusMenu) { this.statusMenu = statusMenu;}


}
