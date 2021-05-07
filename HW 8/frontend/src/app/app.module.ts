import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { YouTubePlayerModule } from '@angular/youtube-player';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MylistComponent } from './components/mylist/mylist.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CarouselComponent } from './components/carousel/carousel.component';
import { GenericCarouselComponent } from './components/generic-carousel/generic-carousel.component';
import { FooterComponent } from './components/footer/footer.component';
import { WatchComponent } from './components/watch/watch.component';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    NavbarComponent,
    MylistComponent,
    CarouselComponent,
    GenericCarouselComponent,
    FooterComponent,
    WatchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    TypeaheadModule.forRoot(),
    FormsModule,
    YouTubePlayerModule
  ],

  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
