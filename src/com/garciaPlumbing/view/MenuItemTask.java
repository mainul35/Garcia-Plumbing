/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import com.garciaPlumbing.view.util.OnMenuItemSelectionChanged;
import javafx.scene.Scene;

/**
 *
 * @author FURIOUS
 */
public class MenuItemTask implements OnMenuItemSelectionChanged {
    Layout_Default layout_Default = new Layout_Default();
    Layout_InsertFittingDetails layout_InsertFittingDetails = new Layout_InsertFittingDetails();
    Layout_AddFitting layout_AddFitting = new Layout_AddFitting();
    Layout_AddSupplier layout_AddSupplier = new Layout_AddSupplier();
    Layout_AddJobType layout_AddJobType = new Layout_AddJobType();
    Layout_AddLabour layout_AddLabour = new Layout_AddLabour();

    @Override
    public boolean doInBackground(int i) {
        try {
            if (i == 0) {
                BasicLayout.getMainScreen().setCenter(layout_Default);
                Scene scene = new Scene(BasicLayout.getMainScreen());
                BasicLayout.stage.setScene(scene);
            } else if (i == 1) {
                BasicLayout.borderPane.setCenter(layout_InsertFittingDetails);
                Scene scene = new Scene(BasicLayout.borderPane);
                BasicLayout.stage.setScene(scene);
            } else if (i == 2) {
                BasicLayout.borderPane.setCenter(new Layout_ViewFittingDetails());
                Scene scene = new Scene(BasicLayout.borderPane);
                BasicLayout.stage.setScene(scene);
            } else if (i == 3) {
                BasicLayout.borderPane.setCenter(layout_AddLabour);
                Scene scene = new Scene(BasicLayout.borderPane);
                BasicLayout.stage.setScene(scene);
            } else if (i == 4) {
                BasicLayout.borderPane.setCenter(new Layout_ViewLabourDetails());
                Scene scene = new Scene(BasicLayout.borderPane);
                BasicLayout.stage.setScene(scene);
            } else if (i == 5) {
                try {
                    BasicLayout.borderPane.setCenter(new Layout_ViewCustomerDetails());
                    Scene scene = new Scene(BasicLayout.borderPane);
                    BasicLayout.stage.setScene(scene);
                } catch (IllegalArgumentException e) {
                }
            } else if (i == 6) {
                BasicLayout.borderPane.setCenter(new Layout_ViewJobOrders());
                Scene scene = new Scene(BasicLayout.borderPane);
                BasicLayout.stage.setScene(scene);
            } else if (i == 7) {
                BasicLayout.borderPane.setCenter(new Layout_AddFinishedJobDetails());
                Scene scene = new Scene(BasicLayout.borderPane);
                BasicLayout.stage.setScene(scene);
            } else if (i == 8) {
                BasicLayout.borderPane.setCenter(new Layout_ViewFinishedJobs());
                Scene scene = new Scene(BasicLayout.borderPane);
                BasicLayout.stage.setScene(scene);
            }
        } catch (IllegalArgumentException e) {
        }

        return false;
    }

}
