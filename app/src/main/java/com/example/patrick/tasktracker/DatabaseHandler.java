package com.example.patrick.tasktracker;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import com.parse.GetCallback;
        import com.parse.ParseException;
        import com.parse.ParseObject;
        import com.parse.ParseQuery;
        import com.parse.SaveCallback;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.TimeZone;

/**
 * Created by Patrick on 10/5/2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // all static variables
    private static final String KEY_Sync_id = "Sync_id";
    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NAME = "Employee_manager";

    // table names-
    private static final String TABLE_EMPLOYEES = "Employee";
    private static final String TABLE_LOCATION = "Location";
    private static final String TABLE_DEPARTMENT = "Department";
    private static final String TABLE_MATERIAL = "Material";
    private static final String TABLE_WORKORDER = "Work_order";
    private static final String TABLE_EMP_WO = "Emp_Work_order";

    // employee table column names
    private static final String KEY_Eagle_id = "Eagle_id";
    private static final String KEY_User_name = "User_name";
    private static final String KEY_First_name = "First_name";
    private static final String KEY_Last_name = "Last_name";
    private static final String KEY_Password = "Password";
    private static final String KEY_Admin = "Admin";
    private static final String KEY_Sync_timestamp = "Sync_timestmap";

    // department table column names
    private static final String KEY_Department_id = "Department_id";
    private static final String KEY_Charged = "Charged";

    // material table column names
    private static final String KEY_Material_id = "Material_id";
    private static final String KEY_Material_name = "Material_name";

    // department table column names
    private static final String KEY_Location_id = "Location_id";
    private static final String KEY_Location_name = "Location_name";

    // workorder table column names
    private static final String KEY_WO_id = "WO_id";
    private static final String KEY_Work_description = "Work_description";
    private static final String KEY_Schedule_Date = "Schedule_date";
    private static final String KEY_Start_time = "Start_time";
    private static final String KEY_End_time = "End_time";

    // Emp_Work_order table column names;
    private static final String KEY_Emp_WO_id = "Emp_WO_id";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db){

        String CREATE_EMPLOYEES_TABLE = "Create Table "
                        + TABLE_EMPLOYEES + "("
                        + KEY_Sync_id + " TEXT, "
                        + KEY_Eagle_id + " INTEGER PRIMARY KEY,"
                        + KEY_User_name + " TEXT,"
                        + KEY_Password + " TEXT,"
                        + KEY_First_name + " TEXT,"
                        + KEY_Last_name + " TEXT,"
                        + KEY_Admin + " TEXT,"
                        + KEY_Sync_timestamp + " DATE)";
                String CREATE_DEPARTMENT_TABLE = "Create Table "
                        + TABLE_DEPARTMENT + "("
                        + KEY_Sync_id + " TEXT, "
                        + KEY_Department_id + " INTEGER PRIMARY KEY,"
                        + KEY_Charged + " TEXT)";
                String CREATE_LOCATION_TABLE = "Create Table "
                        + TABLE_LOCATION + "("
                        + KEY_Sync_id + " TEXT, "
                        + KEY_Location_id + " INTEGER PRIMARY KEY,"
                        + KEY_Location_name + " TEXT,"
                        + KEY_Department_id + " INTEGER, " + " FOREIGN KEY(" + KEY_Department_id + ") REFERENCES " + TABLE_DEPARTMENT + "(" + KEY_Department_id + ")"
                        + ")";
                String CREATE_MATERIAL_TABLE = "Create Table "
                        + TABLE_MATERIAL + "("
                        + KEY_Sync_id + " TEXT, "
                        + KEY_Material_id + " INTEGER PRIMARY KEY,"
                        + KEY_Material_name + " TEXT)";
                String CREATE_WORKORDER_TABLE = "Create Table "
                        + TABLE_WORKORDER + "("
                        + KEY_Sync_id + " TEXT, "
                        + KEY_WO_id + " INTEGER PRIMARY KEY,"
                        + KEY_Work_description + " TEXT,"
                        + KEY_Location_id + " INTEGER,"
                        + KEY_Eagle_id + " INTEGER,"
                        + KEY_Material_id + " INTEGER,"
                        + " FOREIGN KEY(" + KEY_Location_id + ") REFERENCES "+ TABLE_LOCATION + "(" + KEY_Location_id + "),"
                        + " FOREIGN KEY(" + KEY_Eagle_id + ") REFERENCES " + TABLE_EMPLOYEES + "(" + KEY_Eagle_id + "),"
                        + " FOREIGN KEY(" + KEY_Material_id + ") REFERENCES " + TABLE_DEPARTMENT + "(" + KEY_Material_id + ")"
                        +")";
                String CREATE_EMP_WORK_ORDER_TABLE = "Create Table "
                        + TABLE_EMP_WO + "("
                        + KEY_Emp_WO_id + " TEXT,"
                        + KEY_Eagle_id + " INTEGER,"
                        + KEY_WO_id + " INTEGER,"
                        + " FOREIGN KEY(" + KEY_Eagle_id + ") REFERENCES " + TABLE_EMPLOYEES + "(" + KEY_Eagle_id + "),"
                        + " FOREIGN KEY(" + KEY_WO_id + ") REFERENCES " + TABLE_WORKORDER + "(" + KEY_WO_id + ")"
                        + ")";
        db.execSQL(CREATE_EMPLOYEES_TABLE);
        db.execSQL(CREATE_DEPARTMENT_TABLE);
        db.execSQL(CREATE_LOCATION_TABLE);
        db.execSQL(CREATE_MATERIAL_TABLE);
        db.execSQL(CREATE_WORKORDER_TABLE);
        db.execSQL(CREATE_EMP_WORK_ORDER_TABLE);
        Log.d("Database Creation", "Complete");
    }

    // upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // drop oder table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);

        // create tables again
        onCreate(db);
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------Employee Table Methods---------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------
    // adding new employee
    public void addEmployee(final Employee employee, boolean newEntry){
        final ParseObject parseEmployeeObject = new ParseObject("Employee");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final SQLiteDatabase db = this.getWritableDatabase();


        Log.d("addEmployee", employee.getFirst_name() + " " + employee.getLast_name());

        if(newEntry) {
            parseEmployeeObject.put(KEY_User_name, employee.getUser_name());
            parseEmployeeObject.put(KEY_Password, employee.getPassword());
            parseEmployeeObject.put(KEY_First_name, employee.getFirst_name());
            parseEmployeeObject.put(KEY_Last_name, employee.getLast_name());
            parseEmployeeObject.put(KEY_Admin, employee.getAdmin());
            parseEmployeeObject.put("DeleteFlag", 0);
            parseEmployeeObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        ContentValues values = new ContentValues();
                        values.put(KEY_Sync_id, parseEmployeeObject.getObjectId());
                        values.put(KEY_User_name, employee.getUser_name());
                        values.put(KEY_Password, employee.getPassword());
                        values.put(KEY_First_name, employee.getFirst_name());
                        values.put(KEY_Last_name, employee.getLast_name());
                        values.put(KEY_Admin, employee.getAdmin());
                        values.put(KEY_Sync_timestamp, dateFormat.format(System.currentTimeMillis()));
                        // inserting row
                        db.insert(TABLE_EMPLOYEES, null, values);


                        Log.d("Parse", parseEmployeeObject.getObjectId() + " " + parseEmployeeObject.get("First_name"));
                        Log.d("Database",employee.getSync_id() + " " + employee.getFirst_name() +" inserted at " + System.currentTimeMillis());
                    } else {
                        Log.d("Parse", "Could not complete save");
                    }
                }
            });

        }
        db.close();

    }

    // getting a single employee
    public Employee getEmployee(int Eagle_id){
        SQLiteDatabase db = this.getReadableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Cursor cursor = db.query(TABLE_EMPLOYEES, new String[]{ KEY_Eagle_id,
                        KEY_User_name,
                        KEY_Password,
                        KEY_First_name,
                        KEY_Last_name,
                        KEY_Admin,
                        KEY_Sync_timestamp}, KEY_Eagle_id +  " =? ",
                        new String[] { String.valueOf(Eagle_id) },
                        null, // group by
                        null, // having
                        null, // order by
                        null); // limit

        if(cursor.moveToFirst()) {

            Date date;
            try{
                date = dateFormat.parse(cursor.getString(6));
            }catch (Exception e){
                throw new IllegalArgumentException();
            }
            // cursor.getString(0) is the Primary Key. so start at 1.
            Employee employee = new Employee(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), date);

            Log.d("getEmployee("+Eagle_id+")", employee.getFirst_name() + " " + employee.getLast_name());
            db.close();
            return employee;
        }

        db.close();
        return null;
    }

    //getting all employees
    public List<Employee> getAllEmployees(){
        List<Employee> employeeList = new ArrayList<Employee>();
        // select all query
        String selectQuery = "Select "
                + KEY_Eagle_id + ","
                + KEY_User_name + ","
                + KEY_Password + ","
                + KEY_First_name + ","
                + KEY_Last_name + ","
                + KEY_Admin + ","
                + KEY_Sync_timestamp
                + " FROM " + TABLE_EMPLOYEES;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));



        // looping through all rows and adding to the list
        if(cursor.moveToFirst()){
            Date date;
            try{
                date = dateFormat.parse(cursor.getString(6));
            }catch (Exception e){
                throw new IllegalArgumentException(cursor.getString(6));
            }
            do{

                Employee employee = new Employee();

                employee.setUser_name(cursor.getString(1));
                employee.setPassword(cursor.getString(2));
                employee.setFirst_name(cursor.getString(3));
                employee.setLast_name(cursor.getString(4));
                employee.setAdmin(cursor.getString(5));
                employee.setSync_timestamp(date);

                employeeList.add(employee);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return employee list
        return employeeList;
    }

    //getting employees count
    public int getEmployeesCount(){
        int count;
        String countQuery = "SELECT " + KEY_Eagle_id + " FROM " + TABLE_EMPLOYEES;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();

        cursor.close();
        db.close();
        // return count
        return count;
    }

    // updating single employee
    public int updateEmployee(Employee employee){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Eagle_id, employee.getEagle_id());
        values.put(KEY_User_name, employee.getUser_name());
        values.put(KEY_Password, employee.getPassword());
        values.put(KEY_First_name, employee.getFirst_name());
        values.put(KEY_Last_name, employee.getLast_name());
        values.put(KEY_Admin, employee.getAdmin());

        // updating row
        return db.update(TABLE_EMPLOYEES, values, KEY_Eagle_id + " = ?",
                new String[] { String.valueOf(employee.getEagle_id()) });
    }

    // deleting single employee
    public void deleteEmployee(final Employee employee){
        SQLiteDatabase db = this.getWritableDatabase();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Employee");
        query.getInBackground(employee.getSync_id(), new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Log.d("Parse", object.getObjectId() + " " + object.get("First_name") + " = " + employee.getFirst_name());
                    // object will be your employee row
                    object.put("DeleteFlag", 1);

                    object.saveInBackground();
                } else {
                    // something went wrong
                    Log.d("Parse Deletion error", "Error deleting " + employee.getFirst_name() + " from Parse");
                }
            }
        });

        db.delete(TABLE_EMPLOYEES, "Sync_id" + " = ?",
                new String[] { String.valueOf(employee.getSync_id()) });
        db.close();
    }
    public Cursor getData(String query){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------Employee Table Methods End-----------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------


//---------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------Location Table Methods---------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------

public void addLocation(Location location, Department department, Boolean newEntry){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("addLocation", location.getLocation_id() + " " + location.getLocation_name());
        ParseObject parseLocationObject  = new ParseObject("Employee");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(newEntry) {
            parseLocationObject.put(KEY_Location_name, location.getLocation_name());
            parseLocationObject.put("parent", ParseObject.createWithoutData("Department", department.getSync_id()));

            parseLocationObject.saveInBackground();
        }

        ContentValues values = new ContentValues();
        values.put(KEY_Location_id, location.getLocation_id());
        values.put(KEY_Location_name, location.getLocation_name());
        values.put(KEY_Department_id, department.getDepartment_id());

        // inserting row
        db.insert(TABLE_LOCATION, null, values);

        db.close();

    }

    // getting a single location
    public Location getLocation(int Location_id){
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Cursor cursor = db.query(TABLE_LOCATION, new String[]{ KEY_Location_id,
                        KEY_Location_name,
                        KEY_Department_id,
                        KEY_Sync_timestamp}, KEY_Location_id +  " =? ",
                        new String[] { String.valueOf(Location_id) },
                        null, // group by
                        null, // having
                        null, // order by
                        null); // limit

        if(cursor.moveToFirst()) {
            Date date;
            try{
                date = dateFormat.parse(cursor.getString(3));
            }catch(Exception e){
                throw new IllegalArgumentException();
            }
            // cursor.getString(0) is the Primary Key. so start at 1.
            Location location = new Location(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2));

            Log.d("getLocation("+Location_id+")", location.getLocation_name() + " " + location.getDepartment_id());

            return location;
        }


        return null;
    }

    //getting all locations
    public List<Location> getAllLocations(){
        List<Location> locationList = new ArrayList<Location>();
        // select all query
        String selectQuery = "Select * FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        // looping through all rows and adding to the list
        if(cursor.moveToFirst()){
            do{
                Location location = new Location();

                location.setLocation_id(Integer.parseInt(cursor.getString(0)));
                location.setLocation_name(cursor.getString(1));


                locationList.add(location);
            }while(cursor.moveToNext());
        }
        cursor.close();
        // return employee list
        return locationList;
    }

//---------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------Location Table Methods End-----------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------
}
