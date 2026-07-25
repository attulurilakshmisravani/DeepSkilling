// Hands-On 7: full route configuration - params, nested routes, lazy loading, guards
import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { CourseListComponent } from './pages/course-list/course-list.component';
import { CourseDetailComponent } from './pages/course-detail/course-detail.component';
import { StudentProfileComponent } from './pages/student-profile/student-profile.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'courses',
    children: [
      { path: '', component: CourseListComponent },
      { path: ':id', component: CourseDetailComponent }, // route parameter :id
    ],
  },
  { path: 'profile', component: StudentProfileComponent, canActivate: [authGuard] },
  {
    // Hands-On 7 Task 2: lazy loaded feature route (standalone components use loadComponent/loadChildren)
    path: 'enroll',
    loadChildren: () => import('./pages/enrollment-form/enrollment.routes').then((m) => m.ENROLLMENT_ROUTES),
    canActivate: [authGuard],
  },
  { path: '**', component: NotFoundComponent }, // wildcard must always be last
];
