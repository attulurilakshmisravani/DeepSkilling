// Hands-On 2: @Input/@Output + ngOnChanges
// Hands-On 3: ngClass/ngStyle, custom highlight directive, custom creditLabel pipe, ngSwitch badge
// Hands-On 6: injects EnrollmentService to toggle Enroll/Unenroll
import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Course } from '../../models/course.model';
import { CreditLabelPipe } from '../../pipes/credit-label.pipe';
import { HighlightDirective } from '../../directives/highlight.directive';
import { EnrollmentService } from '../../services/enrollment.service';

@Component({
  selector: 'app-course-card',
  standalone: true,
  imports: [CommonModule, CreditLabelPipe, HighlightDirective],
  templateUrl: './course-card.component.html',
  styleUrl: './course-card.component.css',
})
export class CourseCardComponent {
  @Input() course!: Course;
  @Output() enrollRequested = new EventEmitter<number>();

  isExpanded = false;

  constructor(private enrollmentService: EnrollmentService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['course']) {
      console.log('course changed - previous:', changes['course'].previousValue, 'current:', changes['course'].currentValue);
    }
  }

  get cardClasses() {
    return {
      'card--enrolled': this.isEnrolled(),
      'card--full': this.course?.credits >= 4,
      expanded: this.isExpanded,
    };
  }

  get borderStyle() {
    const colors: Record<string, string> = { passed: 'green', failed: 'red', pending: 'grey' };
    return { 'border-left-color': colors[this.course?.gradeStatus] ?? 'grey' };
  }

  isEnrolled(): boolean {
    return this.enrollmentService.isEnrolled(this.course.id);
  }

  toggleEnroll(): void {
    if (this.isEnrolled()) {
      this.enrollmentService.unenroll(this.course.id);
    } else {
      this.enrollmentService.enroll(this.course.id);
      this.enrollRequested.emit(this.course.id);
    }
  }

  toggleDetails(): void {
    this.isExpanded = !this.isExpanded;
  }
}
