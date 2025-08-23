document.addEventListener("DOMContentLoaded", () => {
  fetchCourses();

  const courseForm = document.getElementById("course-form");
  courseForm.addEventListener("submit", (event) => {
    event.preventDefault();
    addCourses();
  });
});

async function fetchCourses() {
  try {
    const response = await fetch("http://localhost:8081/api/courses");
    const courses = await response.json();
    const tablebody = document.querySelector("#courses-table tbody");
    tablebody.innerHTML = "";

    courses.forEach((course) => {
      const row = tablebody.insertRow();
      row.insertCell(0).textContent = course.courseId;
      row.insertCell(1).textContent = course.courseName;
      row.insertCell(2).textContent = course.courseCode;
      row.insertCell(3).textContent = course.courseDescription;

      const actionsCell = row.insertCell(4);
      const deleteButton = document.createElement("button");
      deleteButton.textContent = "Delete";
      deleteButton.onclick = () => deleteCourse(course.courseId);
      actionsCell.appendChild(deleteButton);
    });
  } catch (error) {
    console.log("Error fetching courses:", error);
  }
}

async function addCourses() {
  const courseName = document.getElementById("courseName").value;
  const courseCode = document.getElementById("courseCode").value;
  const courseDescription = document.getElementById("courseDescription").value;

  const newCourse = {
    courseName,
    courseCode,
    courseDescription,
  };
  try {
    const response = await fetch("http://localhost:8081/api/courses", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newCourse),
    });
    if (response.ok) {
      console.log("course added successfully");
      document.getElementById("course-form").reset();
      fetchCourses(); // Refresh the list
    } else {
      console.error("Failed to add course.");
    }
  } catch (error) {
    console.log("Error adding course", error);
  }
}
async function deleteCourse(courseId) {
  try {
    const response = await fetch(
      `http://localhost:8081/api/courses/${courseId}`,
      {
        method: "DELETE",
      }
    );

    if (response.ok) {
      console.log("Course deleted successfully!");
      fetchCourses(); // Refresh the list
    } else {
      console.error("Failed to delete course.");
    }
  } catch (error) {
    console.error("Error deleting course:", error);
  }
}
