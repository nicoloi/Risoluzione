#  this script tests the resolution method on all txt files contained
#  within the "test" directory. These files contain the clause sets to test. 
#  The script separates the satisfiable clause sets from the unsatisfiable ones, 
#  and checks whether the string "UNSATISFIABLE" does not appear in the output 
#  of the satisfiable ones, while it must appear in the output of the 
#  unsatisfiable ones. If one or more files fail the test, then the script 
#  prints the list of txt files that failed the test.


import os

def get_output(file):
    p = os.popen(f'java Test < ./test/{file}')
    out = p.read()
    return out




rejectedFiles = [] #list of files that failed the test

for direc, subdir, files in os.walk('test'):
    for f in files:
        if f.startswith('sat'):
            out = get_output(f)
            if out.find("UNSATISFIABLE") != -1: #if we find the substring "UNSATISFIABLE" in the output
                rejectedFiles.append(f)
        elif f.startswith('unsat'):
            out = get_output(f)
            if out.find("UNSATISFIABLE") == -1: #if we don't find the substring "UNSATISFIABLE" in the output
                rejectedFiles.append(f)




if not rejectedFiles: #if the list is empty
    print("Test PASSED successfully")
else:
    for file in rejectedFiles:
        print(f'Test FAILED at the file "{file}"')


