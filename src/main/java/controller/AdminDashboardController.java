package controller;

import view.AdminDashboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardController {
    private AdminDashboard adminDashboard;

    public AdminDashboardController(AdminDashboard adminDashboard) {
        this.adminDashboard = adminDashboard;
    }

    public ActionListener createPostsActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminDashboard.showPostsOption();
            }
        };
    }

    public ActionListener createAddPostActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminDashboard.showAddPostOption();
            }
        };
    }

    public ActionListener createDeletePostActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminDashboard.showDeletePostOption();
            }
        };
    }

    public ActionListener createUpdatePostActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminDashboard.showUpdatePostOption();
            }
        };
    }
}
