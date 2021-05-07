import { Component, OnInit, Injectable } from '@angular/core';
import { Observable, Subscriber, OperatorFunction, of, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, catchError, mergeMap, tap, map,merge  } from 'rxjs/operators';
import {PostsService} from '../../services/posts.service';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap'

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  mobile:any;
  isCollapsed = true;
  model: any;
  focus$ = new Subject<string>();

  constructor(private postsService: PostsService) {
    if (window.screen.width < 500) {
      this.mobile = true;
    } else {
      this.mobile = false;
    }

  }


  formatter = (x: { name: string}) => x.name;



  search: OperatorFunction<string, readonly {name:any, backdrop_path:any, id: any, media_type: any}[]> = (text$: Observable<string>) =>

    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => {
        if (term.length < 1){



          return []
        } else {
          return this.postsService.getSearch(term)
        }
      })

    )

    selectNt(item:any) {
      location.href = '/watch/' + item.media_type + '/' + item.id
    }


}
