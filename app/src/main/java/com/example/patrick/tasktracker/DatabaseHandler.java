package com.example.patrick.tasktracker;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import com.parse.Parse;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Patrick on 10/5/2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // all static variables
    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NAME = "Employee_manager";

    // employee table name
    private static final String TABLE_EMPLOYEES = "Employee";

    // employee table column names
    private static final String KEY_Eagle_id = "Eagle_id";
    private static final String KEY_User_name = "User_name";
    private static final String KEY_First_name = "First_name";
    private static final String KEY_Last_name = "Last_name";
    private static final String KEY_Password = "Password";
    private static final String KEY_Admin = "Admin";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db){
                String CREATE_EMPLOYEES_TABLE = "Create Table " + TABLE_EMPLOYEES + "("
                        + KEY_Eagle_id + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_User_name + " TEXT,"
                        + KEY_Password + " TEXT," + KEY_First_name + " TEXT," + KEY_Last_name + " TEXT,"
                        + KEY_Admin + " TEXT)";
        db.execSQL(CREATE_EMPLOYEES_TABLE);
    }

    // upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // drop oder table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);

        // create tables again
        onCreate(db);
    }

    // adding new employee
    public void addEmployee(Employee employee){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Adding: ", "adding a new employee to the table.");
        ContentValues values = new ContentValues();
       // values.put(KEY_Eagle_id, employee.getEagle_id());
        values.put(KEY_User_name, employee.getUser_name());
        values.put(KEY_Password, employee.getPassword());
        values.put(KEY_First_name, employee.getFirst_name());
        values.put(KEY_Last_name, employee.getLast_name());
        values.put(KEY_Admin, employee.getAdmin());

        // inserting row
        db.insert(TABLE_EMPLOYEES, null, values);


        // for logcat only
        Cursor cursor = db.query(TABLE_EMPLOYEES, new String[]{ KEY_Eagle_id},null,null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        Log.d("Keyvalue: ", cursor.getString(0));
        // for logcat only


        db.close();

    }

    // getting a single employee
    public Employee getEmployee(int Eagle_id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMPLOYEES, new String[]{ KEY_Eagle_id,
                        KEY_User_name, KEY_Password, KEY_First_name, KEY_First_name, KEY_Last_name,
                        KEY_Admin}, KEY_Eagle_id + "=?",
                new String[] { String.valueOf(Eagle_id) }, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();
        // cursor.getString(0) is the Primary Key. so start at 1.
        Employee employee = new Employee(cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        return employee;
    }

    //getting all employees
    public List<Employee> getAllEmployees(){
        List<Employee> employeeList = new ArrayList<Employee>();
        // select all query
        String selectQuery = "Select * FROM " + TABLE_EMPLOYEES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        // looping through all rows and adding to the list
        if(cursor.moveToFirst()){
            do{
                Employee employee = new Employee();

                employee.setUser_name(cursor.getString(1));
                employee.setPassword(cursor.getString(2));
                employee.setFirst_name(cursor.getString(3));
                employee.setLast_name(cursor.getString(4));
                employee.setAdmin(cursor.getString(5));

                employeeList.add(employee);
            }while(cursor.moveToNext());
        }
        cursor.close();
        // return employee list
        return employeeList;
    }

    //getting employees count
    public int getEmployeesCount(){
        int count;
        String countQuery = "SELECT  * FROM " + TABLE_EMPLOYEES;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();

        cursor.close();

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
    public void deleteEmployee(Employee employee){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEES, KEY_Eagle_id + " = ?",
                new String[] { String.valueOf(employee.getEagle_id()) });
        db.close();
    }
    public Cursor getData(String query){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
