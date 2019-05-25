//
//  ViewController.swift
//  KotlinIOS
//
//  Created by Андрей Берюхов on 06.10.2018.
//  Copyright © 2018 Beryukhov. All rights reserved.
//

import UIKit
import SharedCode

class ViewController: UIViewController, TimeSheetView {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let presenter = TimeSheetPresenterKmp(timeSheetView:self, timeSheetRepository:TimeSheetRepositoryImplSwift())
        presenter.onCreateView()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func addAll(list: [DateModel]) {
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
        label.center = CGPoint(x: 160, y: 285)
        label.textAlignment = .center
        label.font = label.font.withSize(25)
        label.text = list.count.description
        view.addSubview(label)
    }
    
    func clear() {
        
    }
    
    func showError(message: String) {
        
    }
    
    func showProgress() {
        
    }
    
    func hideProgress() {
        
    }
    
    class TimeSheetRepositoryImplSwift: TimeSheetRepository{
        
    }
    
}

