// Hands-On 4: template-driven form with built-in validators and contextual error messages
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-enrollment-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './enrollment-form.component.html',
  styleUrl: './enrollment-form.component.css',
})
export class EnrollmentFormComponent {
  studentName = '';
  studentEmail = '';
  courseId: number | null = null;
  preferredSemester: 'Odd' | 'Even' = 'Odd';
  agreeToTerms = false;

  submitted = false;

  constructor(private courseService: CourseService) {}

  onSubmit(form: NgForm): void {
    console.log(form.value, form.valid);
    if (form.valid) {
      this.submitted = true;
      // Hands-On 8, Task 1, Step 81: wired to the createCourse POST for demonstration purposes
      this.courseService
        .createCourse({
          name: `Enrollment request for course ${this.courseId}`,
          code: `REQ-${this.courseId}`,
          credits: 0,
          gradeStatus: 'pending',
        })
        .subscribe();
    }
  }
}
