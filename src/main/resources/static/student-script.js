document.addEventListener("DOMContentLoaded", () => {
  // Define STUDENT_ID here so it's in the correct scope
  const STUDENT_ID = 5;
  const BASE_URL = "http://localhost:8081";

  fetchAvailableCourses(BASE_URL);
  fetchEnrolledCourses(STUDENT_ID, BASE_URL);
});

async function fetchAvailableCourses(BASE_URL) {
  try {
    const response = await fetch(`${BASE_URL}/api/courses`, {
      credentials: "include",
    });
    const courses = await response.json();
    const tableBody = document.querySelector("#available-courses-table tbody");
    tableBody.innerHTML = "";

    courses.forEach((course) => {
      const row = tableBody.insertRow();
      row.insertCell(0).textContent = course.courseId;
      row.insertCell(1).textContent = course.courseName;
      row.insertCell(2).textContent = course.courseCode;

      const actionsCell = row.insertCell(3);
      const enrollButton = document.createElement("button");
      enrollButton.textContent = "Enroll";
      enrollButton.onclick = () => enrollInCourse(course.courseId, BASE_URL);
      actionsCell.appendChild(enrollButton);
    });
  } catch (error) {
    console.error("Error fetching available courses:", error);
  }
}

async function enrollInCourse(courseId, BASE_URL) {
  // STUDENT_ID is no longer defined in this function
  try {
    const response = await fetch(
      `${BASE_URL}/api/enrollments/enroll?courseId=${courseId}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
      }
    );

    if (response.ok) {
      console.log("Enrollment successful!");
      alert("You have successfully enrolled in the course!");
      const STUDENT_ID = 5; // Re-declare it for this specific function scope
      fetchEnrolledCourses(STUDENT_ID, BASE_URL);
    } else {
      console.error("Enrollment failed.");
      alert("Enrollment failed. You might already be enrolled in this course.");
    }
  } catch (error) {
    console.error("Error enrolling in course:", error);
  }
}

async function fetchEnrolledCourses(studentId, BASE_URL) {
  try {
    const response = await fetch(
      `${BASE_URL}/api/enrollments/student/${studentId}`,
      {
        credentials: "include",
      }
    );

    const enrollments = await response.json();
    const tableBody = document.querySelector("#my-courses-table tbody");
    tableBody.innerHTML = "";

    enrollments.forEach((enrollment) => {
      const row = tableBody.insertRow();
      row.insertCell(0).textContent = enrollment.enrollmentId;
      row.insertCell(1).textContent = enrollment.course.courseName;
      const enrollmentDate = new Date(enrollment.enrollmentDate);
      row.insertCell(2).textContent = enrollmentDate.toLocaleDateString();

      const actionsCell = row.insertCell(3);
      const dropButton = document.createElement("button");
      dropButton.textContent = "Drop";
      dropButton.onclick = () => dropCourse(enrollment.enrollmentId, BASE_URL);
      actionsCell.appendChild(dropButton);
    });
  } catch (error) {
    console.error("Error fetching enrolled courses:", error);
  }
}

async function dropCourse(enrollmentId, BASE_URL) {
  // STUDENT_ID is no longer defined in this function
  try {
    const response = await fetch(
      `${BASE_URL}/api/enrollments/${enrollmentId}`,
      {
        method: "DELETE",
        credentials: "include",
      }
    );

    if (response.ok) {
      console.log("Course dropped successfully!");
      alert("Course dropped successfully!");
      const STUDENT_ID = 5; // Re-declare it for this specific function scope
      fetchEnrolledCourses(STUDENT_ID, BASE_URL); // Refresh the list
    } else {
      console.error("Failed to drop course.");
      alert("Failed to drop course. Please try again.");
    }
  } catch (error) {
    console.error("Error dropping course:", error);
  }
}
