// Hands-On 6, Task 2, Step 67: providing NotificationService here (component-level `providers`)
// creates a brand-new instance scoped to this component and its children, separate from
// any other instance of NotificationService elsewhere in the app - useful for isolated,
// per-instance state (e.g. a toast list that shouldn't leak between widgets).
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [CommonModule],
  providers: [NotificationService],
  templateUrl: './notification.component.html',
})
export class NotificationComponent {
  constructor(public notificationService: NotificationService) {}

  addSample(): void {
    this.notificationService.push('You have a new course update.');
  }
}
