package Maja;

//===================== Maja, All rights reserved. ======================//
//
// Purpose: User defined settings class for Maja.
//
//=======================================================================

import java.nio.file.Path;
import java.util.ArrayList;

public class MajaSettings extends Maja {

    protected static boolean debug = false;
    protected static ArrayList<String> routes = new ArrayList<>();
    protected static ArrayList<Runnable> routeCallbacks = new ArrayList<>();

}
