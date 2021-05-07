import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { MylistComponent } from './components/mylist/mylist.component';
import { WatchComponent } from './components/watch/watch.component';
import { NavbarComponent } from './components/navbar/navbar.component'
const routes: Routes = [
  {path: '', component: HomepageComponent},
  {path: 'mylist', component: MylistComponent},
  {path: 'watch/:type/:id', component: WatchComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
