// Hands-On 7, Task 2, Step 73: feature routes, lazily loaded under /enroll
import { Routes } from '@angular/router';
import { EnrollmentFormComponent } from './enrollment-form.component';
import { ReactiveEnrollmentFormComponent } from '../reactive-enrollment-form/reactive-enrollment-form.component';
import { unsavedChangesGuard } from '../../guards/unsaved-changes.guard';

export const ENROLLMENT_ROUTES: Routes = [
  { path: '', component: EnrollmentFormComponent },
  { path: 'reactive', component: ReactiveEnrollmentFormComponent, canDeactivate: [unsavedChangesGuard] },
];
