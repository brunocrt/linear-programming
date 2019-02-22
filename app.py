from flask import Flask
from flask import render_template

import numpy as np
from scipy.optimize import linprog
from numpy.linalg import solve
 
app = Flask(__name__)
 
@app.route('/hello/')
@app.route('/hello/<name>')
def hello(name=None):
    return render_template('hello.html', name=name)

@app.route('/linear')
def linear():

    A_ub = np.array([
        [2, 4],
        [6, 1],
        [1,-1]])
    
    b_ub = np.array([10, 20, 30])
    
    c = np.array([-3, -5])
    
    res = linprog(c, A_ub=A_ub, b_ub=b_ub,bounds=(0, None))


    return render_template('linear.html', res=res)

app.run(host='0.0.0.0', port= 8090)