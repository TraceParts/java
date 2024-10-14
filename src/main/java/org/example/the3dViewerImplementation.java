package org.example;

import picocli.CommandLine;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class the3dViewerImplementation implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new the3dViewerImplementation()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Your EasyLink Solutions ID (ELS ID), provided in the email with your Tenant Uid and your API key")
    private String elsid;
    @CommandLine.Parameters(index = "1", description = "Language of the labels.")
    private String cultureInfo;

    // You can enter either Supplier ID + Part number OR Product + Selection path.
    // In case of multiple pairs are given the first one will be taken (Supplier ID + Part number).
    @CommandLine.Option(names = {"--supplierID"}, description = "ClassificationCode provided by the \"availability\" endpoints")
    private String supplierID;
    @CommandLine.Option(names = {"--partNumber"}, description = "Identifier of a product (to use in combination with SupplierID). Part number as stored in the TraceParts database.")
    private String partNumber;
    @CommandLine.Option(names = {"--product"}, description = "PartFamilyCode provided by the \"availability\" endpoints")
    private String product;
    @CommandLine.Option(names = {"--selectionPath"}, description = "Sequence of parameters which defines a unique configuration for one given partFamilyCode.")
    private String selectionPath;

    @CommandLine.Option(names = {"--setBackgroundColor"}, defaultValue = "0xFFFFFF", description = "Sets a color on the background of the 3D viewer.")
    private String setBackgroundColor;
    @CommandLine.Option(names = {"--setRenderMode"}, defaultValue = "shaded-edged", description = "Rendering of the 3D model. Values: “shaded-edged”, “shaded”, “transparent”, “wireframe”, “edged”.")
    private String setRenderMode;
    @CommandLine.Option(names = {"--enableMirrorEffect"}, defaultValue = "false", description = "Enable the mirror effect on the XZ plane.")
    private Boolean enableMirrorEffect;
    @CommandLine.Option(names = {"--displayCoordinateSystem"}, defaultValue = "false", description = "Display the coordinate system.")
    private Boolean displayCoordinateSystem;
    @CommandLine.Option(names = {"--enablePresentationMode"}, defaultValue = "false", description = "The model rotates on the Y axis until a user interaction.")
    private Boolean enablePresentationMode;
    @CommandLine.Option(names = {"--displayUIMenu"}, defaultValue = "true", description = "Display the toolbars (on the bottom and on the right).")
    private Boolean displayUIMenu;
    @CommandLine.Option(names = {"--displayUIContextMenu"}, defaultValue = "true", description = "Enable the contextual menu with Views and Render sub menus.")
    private Boolean displayUIContextMenu;
    @CommandLine.Option(names = {"--mergeUIMenu"}, defaultValue = "false", description = "Merge the contextual menu inside the main menu.")
    private Boolean mergeUIMenu;
    @CommandLine.Option(names = {"--menuAlwaysVisible"}, defaultValue = "false", description = "Always display the toolbar.")
    private Boolean menuAlwaysVisible;
    @CommandLine.Option(names = {"--displayUIResetButtonMenu"}, defaultValue = "true", description = "Display the Reset button.")
    private Boolean displayUIResetButtonMenu;
    @CommandLine.Option(names = {"--displayUIScreenshotButtonMenu"}, defaultValue = "true", description = "Display the Screenshot button.")
    private Boolean displayUIScreenshotButtonMenu;
    @CommandLine.Option(names = {"--displayUISettingsSubMenu"}, defaultValue = "true", description = "Display the Settings menu.")
    private Boolean displayUISettingsSubMenu;
    @CommandLine.Option(names = {"--displayUIPresentationModeButtonMenu"}, defaultValue = "true", description = "Display the Presentation button.")
    private Boolean displayUIPresentationModeButtonMenu;
    @CommandLine.Option(names = {"--displayUIViewsSubContextMenu"}, defaultValue = "true", description = "Display the Views sub menu (for the contextual menu).")
    private Boolean displayUIViewsSubContextMenu;
    @CommandLine.Option(names = {"--displayUIRenderModesSubContextMenu"}, defaultValue = "true", description = "Display the Render sub menu (for the contextual menu).")
    private Boolean displayUIRenderModesSubContextMenu;

    @Override
    public void run() {
        // THERE IS NO API CALL HERE. This function provides a 3D viewer of the 3D model of one given configuration of a catalog
        // documentation : https://developers.traceparts.com/v2/reference/3d-viewer-implementation
        System.out.println(getThe3dViewerImplementationUrl(elsid, cultureInfo, getPossibleOptions()));
    }

    public static String getThe3dViewerImplementationUrl(String elsid, String cultureInfo, HashMap<String, String> possibleOptions) {
        System.out.println("There is no API call here. Inputs are just arranged and formated to get the corresponding URL.");
        HashMap<String, String> defaultValues = getDefaultValues();

        StringBuilder possibleOptionsString = new StringBuilder();
        // Options are formated to be added as GET parameters in the URL
        for (Map.Entry<String, String> parameter : possibleOptions.entrySet()) {
            // We go through all function options and check if they are usable (not empty)
            // And if they are different from their default values
            String key = parameter.getKey();
            String value = parameter.getValue();
            if (value != null && !value.isEmpty() && !value.equals(defaultValues.get(key))) {
                // Encoding the value to avoid special characters
                possibleOptionsString.append(key).append("=").append(URLEncoder.encode(value, StandardCharsets.UTF_8)).append("&");
            }
        }

        String url = "https://www.traceparts.com/els/";
        url += URLEncoder.encode(elsid, StandardCharsets.UTF_8);
        url += "/";
        url += URLEncoder.encode(cultureInfo, StandardCharsets.UTF_8);
        url += "/api/viewer/3d?";
        url += possibleOptionsString;

        return url;
    }

    private static HashMap<String, String> getDefaultValues() {
        // The default values are gathered from the documentation and will be used to create more concise URLs.
        // For example : if EnableMirrorEffect is affected to 'false' in the function call, it will be ignored as it is its default value.
        HashMap<String, String> defaultValues = new HashMap<>();
        defaultValues.put("SupplierID", "");
        defaultValues.put("PartNumber", "");
        defaultValues.put("Product", "");
        defaultValues.put("SelectionPath", "");
        defaultValues.put("SetBackgroundColor", "0xFFFFFF");
        defaultValues.put("SetRenderMode", "shaded-edged");
        defaultValues.put("EnableMirrorEffect", "false");
        defaultValues.put("DisplayCoordinateSystem", "false");
        defaultValues.put("EnablePresentationMode", "false");
        defaultValues.put("DisplayUIMenu", "true");
        defaultValues.put("DisplayUIContextMenu", "true");
        defaultValues.put("MergeUIMenu", "false");
        defaultValues.put("MenuAlwaysVisible", "false");
        defaultValues.put("DisplayUIResetButtonMenu", "true");
        defaultValues.put("DisplayUIScreenshotButtonMenu", "true");
        defaultValues.put("DisplayUISettingsSubMenu", "true");
        defaultValues.put("DisplayUIPresentationModeButtonMenu", "true");
        defaultValues.put("DisplayUIViewsSubContextMenu", "true");
        defaultValues.put("DisplayUIRenderModesSubContextMenu", "true");
        return defaultValues;
    }

    private HashMap<String, String> getPossibleOptions() {
        // This is made to simplify the options management
        HashMap<String, String> optionalParameters = new HashMap<>();
        optionalParameters.put("supplierID", supplierID);
        optionalParameters.put("partNumber", partNumber);
        optionalParameters.put("product", product);
        optionalParameters.put("selectionPath", selectionPath);
        optionalParameters.put("setBackgroundColor", setBackgroundColor);
        optionalParameters.put("setRenderMode", setRenderMode);
        optionalParameters.put("enableMirrorEffect", String.valueOf(enableMirrorEffect));
        optionalParameters.put("displayCoordinateSystem", String.valueOf(displayCoordinateSystem));
        optionalParameters.put("enablePresentationMode", String.valueOf(enablePresentationMode));
        optionalParameters.put("displayUIMenu", String.valueOf(displayUIMenu));
        optionalParameters.put("displayUIContextMenu", String.valueOf(displayUIContextMenu));
        optionalParameters.put("mergeUIMenu", String.valueOf(mergeUIMenu));
        optionalParameters.put("menuAlwaysVisible", String.valueOf(menuAlwaysVisible));
        optionalParameters.put("displayUIResetButtonMenu", String.valueOf(displayUIResetButtonMenu));
        optionalParameters.put("displayUIScreenshotButtonMenu", String.valueOf(displayUIScreenshotButtonMenu));
        optionalParameters.put("displayUISettingsSubMenu", String.valueOf(displayUISettingsSubMenu));
        optionalParameters.put("displayUIPresentationModeButtonMenu", String.valueOf(displayUIPresentationModeButtonMenu));
        optionalParameters.put("displayUIViewsSubContextMenu", String.valueOf(displayUIViewsSubContextMenu));
        optionalParameters.put("displayUIRenderModesSubContextMenu", String.valueOf(displayUIRenderModesSubContextMenu));
        return optionalParameters;
    }
}

