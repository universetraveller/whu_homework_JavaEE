import sqlite3
con = sqlite3.connect("products.db")
cur = con.cursor()
try:
    cur.execute("DROP TABLE products")
    cur.execute("DROP TABLE suppliers")
except:
    pass
cur.execute("CREATE TABLE products(id INTEGER PRIMARY KEY, price FLOAT, name TEXT, brand TEXT, date TEXT)")
cur.execute("CREATE TABLE suppliers(id INTEGER, name TEXT)")
con.commit()
con.close()
