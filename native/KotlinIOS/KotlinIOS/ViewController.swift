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
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder:aDecoder)
        //todo
    }
    
    var presenter:TimeSheetPresenterKmp
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        presenter = TimeSheetPresenterKmp(timeSheetView:self, timeSheetRepository:TimeSheetRepositoryImplSwift())
        presenter.onCreateView()
        
        //let startButton = vie
        /*let myViewControllerInstance : ViewController = (UIStoryboard.init(name: "main", bundle: nil) as! ViewController)
        myViewControllerInstance.start*/
        

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
    
    @IBAction func startButton(sender:UIButton){
        if (sender.restorationIdentifier=="start"){
            presenter.onFixStart()
        }
    }
    
    class TimeSheetRepositoryImplSwift: TimeSheetRepository{
        
    }
    
}

