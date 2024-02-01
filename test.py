#USA IL METODO FIND PER VEDERE SE è SODDISFACIBILE

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
            if out.find("UNSATISFIABLE") != -1: #if in the output we find the substring "UNSATISFIABLE"
                rejectedFiles.append(f)
        elif f.startswith('unsat'):
            out = get_output(f)
            if out.find("UNSATISFIABLE") == -1: #if we don't find the substring "UNSATISFIABLE" in the output
                rejectedFiles.append(f)




#se la lista dei file è vuota
if not rejectedFiles:
    print("Test PASSED successfully")
else:
    for file in rejectedFiles:
        print(f'Test FAILED at the file "{file}"')


