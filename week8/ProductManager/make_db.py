import sqlite3
con = sqlite3.connect("products.db")
cur = con.cursor()
def try_do(cur, sqls):
    try:
        cur.execute(sqls)
    except Exception as e:
        print(e)
def get_users():
    users = []
    users.append(['10000', '$2a$10$mkhB8Pj6t1VX0JVg1EBRbeTuNfBrqG.mz9gv7YhYw9LOunqRrSTUa', 'admin', 101])
    users.append(['10001', '$2a$10$mkhB8Pj6t1VX0JVg1EBRbeTuNfBrqG.mz9gv7YhYw9LOunqRrSTUa', 'user0', 1])
    return users
try_do(cur, "DROP TABLE products")
try_do(cur, "DROP TABLE suppliers")
try_do(cur, "DROP TABLE users")
cur.execute("CREATE TABLE products(id INTEGER PRIMARY KEY, price FLOAT, name TEXT, brand TEXT, date TEXT)")
cur.execute("CREATE TABLE suppliers(id INTEGER, name TEXT)")
cur.execute("CREATE TABLE users(id TEXT, password TEXT, username TEXT, level INTEGER)")
cur.executemany("INSERT INTO users VALUES(?, ?, ?, ?)", get_users())
con.commit()
con.close()
