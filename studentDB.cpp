/*******************************************************************************
Author:        Gustavo Zach Vargas
CS Login:      gustavo

Pair Partner:  <name of your pair programming partner (if applicable)>
CS Login:      <your partner's login name>

Credits:       <name of anyone (other than your pair programming partner) who
                helped you write your program>

Course:         CS 368, Fall 2013
Assignment:     Programming Assignment 1
 ******************************************************************************/


#include <iostream>

using namespace std;


//Struct the defines a student consisting of:
//student ID: a positive 6-digit integer, not starting with zero
//credits: non-negative number of credits taken
//GPA: float from 0.0-4.0
struct Student {
	int studentID;
	int credits;
	double GPA;
};


//variables used throughout
const int SIZE = 5000; // the size of the DB
bool flag = false;
int opIndex = 0; // the next open index in the DB
Student s = {0,0,0}; //an uninitialized student

Student DB[SIZE];

/**
 * Adds a student with the given student ID (ID), number of credits (credits),
 * and overall GPA (GPA) to the database. If the student is already in the
 * database, an error message should be printed indicating this.
 *
 * @param (ID) the student ID of the student to be added
 * @param (credits) the number of credits the student to be added has taken
 * @param (GPA) the gpa of the student to be added
 * @return void
 */
void addStudent (int ID, int credits, double GPA){
	flag = false;

	for(int i = 0; i < SIZE; i++){
		if(DB[i].studentID == ID){
			flag = true;
			cout << "ERROR: This student is already in the database." << endl;
		}
	}

	if(flag == false){
		Student add = {ID, credits, GPA}; //create student with given attributes
		DB[opIndex] = add; //add student to DB
		opIndex++; //increment next open spot
	}
}


/**
 * Deletes the student with the given student ID (ID) from the database.
 * If the student is not in the database,
 * an error message should be printed indicating this.
 *
 * @param (ID) the student ID of the student to be deleted
 * @return void
 */
void deleteStudent(int ID){
	flag = false;

	for(int i = 0; i < SIZE; i++){
		//if the student with ID is found, remove the student
		if(DB[i].studentID == ID){
			//remove the student, set flag to indicate student was found
			DB[i] = s;
			flag = true;

			//if the student removed was not at the end of the list,
			//rearrange the array
			if(i != opIndex-1){

				for(int j = i; j < SIZE; j++){
					DB[j] = DB[j+1];
				}

				//decrement opIndex to reflect one less element
				opIndex--;
			}
		}
	}

	if(flag == false){
		cout << "ERROR: student " << ID << " not found." << endl;
	}

}


/**
 *Computes the integer grade point equivalent to a given letter grade.
 *
 * @param (grade) grade in the class the student recently took
 * @return the integer value corresponding to a letter grade.
 */
int grade2num(char grade){
	int grVal;

	switch(grade){
	case 'A':
		grVal = 4;
		break;

	case 'B':
		grVal = 3;
		break;

	case 'C':
		grVal = 2;
		break;

	case 'D':
		grVal = 1;
		break;

	case 'F':
		grVal = 0;
		break;

	default:
		cout << "ERROR: please retry student update with a grade letter"
		" (A,B,C,D or F)" << endl;
		grVal = -1;
		break;
	}

	return grVal;

}


/**
 *Updates the student with the given student ID (ID) so that both the total
 *number of credits and the overall GPA reflect the addition of a letter grade
 * of grade (param) in a course with N credits.
 *
 *After this command is processed, the program should output the updated student
 *information to the console (cout). If the student is not in the database,
 *information an error message should be printed indicating this.
 *
 * @param (ID) the student ID of the student to be deleted
 * @param (grade) grade in the class the student recently took
 * @param (credits) number of credits in recently taken class
 * @return void
 */
void updateStudent(int ID, char grade, int credits){

	grade = toupper(grade); //accept both lower and upper case letter grade

	//grVal will contain number equivalent value of grade
	int grVal = grade2num(grade);

	// if the grade entered is not recognized
	if(grVal == -1){ return;}

	for(int i = 0; i < SIZE; i++){
		//if the student is in the DB, update their GPA
		if(DB[i].studentID == ID){

			//GPA = total grade points / total credits attempted

			//calculate total grade points = GPA * credits taken
			double gradePoints = DB[i].GPA*DB[i].credits;

			//calculate grade pts of new class
			double classPts = credits * grVal;

			//add grade points of new class to total grade points
			gradePoints += classPts;

			//add credits of new class to total credits
			DB[i].credits = DB[i].credits + credits;

			//recalculate GPA
			DB[i].GPA = gradePoints / DB[i].credits;

		}
	}
}

/**
 *Prints the current contents of the student database.
 *
 * @return void
 */
void print(){

	cout << "ID:     " << "Cr: " << "GPA: " << endl;

	for(int i = 0; i< SIZE; i++){
		//once the student ID is equal to 0, there are no more students
		if(DB[i].studentID == 0){
			i = SIZE;
		}

		else{
			cout << DB[i].studentID << ", " << DB[i].credits
					<< ", " << DB[i].GPA << endl;
		}
	}
}

/**
 * Check if the given student ID is within valid range:
 * Positive six digit number, first digit is not 0
 *
 * @param (ID) the student ID of the student to be added
 * @return integer 0 if input is valid, 1 if input is not valid
 */
int checkID(int ID) {

	if(ID < 0){
		cout << "ERROR: student ID must be a non-negative int" << endl;
		return 1;
	}

	if(ID > 999999 | ID < 100000){
		cout << "ERROR: student ID must be a six digit number. "
				"The first digit of which can not be 0." << endl;
		return 1;
	}

	return 0;
}

/**
 * Check if the given credit number is within valid range:
 * Positive integer
 *
 * Check if the given GPA is valid: double 0.0 - 4.0 inclusive
 *
 * @param (credits) total credits taken my a student
 * @param (GPA) grade point avg of student
 * @return integer 0 if input is valid, 1 if input is not valid
 */
int checkCrtsGPA(char credits, double GPA) {

	if(credits < 0){
		cout << "ERROR: credits must be a non-negative integer" << endl;
		return 1;
	}

	if(GPA < 0.0 | GPA > 4.0){
		cout << "ERROR: GPA must be between 0.0 - 4.0 (inclusive)" << endl;
		return 1;
	}

	return 0;
}


/**
 *Main method that prompts user for input and adjusts the database accordingly
 *
 * @return integer
 */
int main() {
	bool done = false;
	char choice;
	int studentID;
	int credits;
	double GPA;
	char grade;


	cout << "Enter your commands at the > prompt:" << endl;
	while (!done) {
		cout << "> ";
		cin >> choice;


		if(opIndex == 4999){
			done = true;
		}

		switch (choice) {

		case 'a':
			cin >> studentID >> credits >> GPA;

			//check if all input is valid. If not, break
			if(checkID(studentID) == 1){
				break;
			}
			if(checkCrtsGPA(credits, GPA) == 1){
				break;
			}

			//if input is valid, add the student with given params
			addStudent(studentID, credits, GPA);
			break;

		case 'd':
			cin >> studentID;  // read in the integer ID

			//check if all input is valid. If not, break
			if(checkID(studentID) == 1){
				break;
			}

			deleteStudent(studentID);
			break;

		case 'u':
			cin >> studentID >> grade >> credits;

			//check if all input is valid. If not, break
			if(checkID(studentID) == 1){
				break;
			}
			if(checkCrtsGPA(credits, GPA) == 1){
				break;
			}

			updateStudent(studentID, grade, credits);
			break;

		case 'p':
			print();
			break;

		case 'q':
			done = true;
			cout << "quit" << endl;
			break;

			// If the command is not one listed in the specification, for the
			// purposes of this assignment, we will ignore it.  Note that you
			// will see multiple ?'s printed out if there is additional
			// information on the line (in addition to the unknown command
			// character).
		default: break;
		} // end switch

	} // end while

	return 0;
}// end main

