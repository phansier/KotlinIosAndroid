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
    var datesList = [DateModel]()
    
    @IBOutlet weak var tableView: UITableView!
    
    @IBAction func onFixStartButton(_ sender: UIButton) {
        self.presenter?.onFixStart()
    }
    @IBAction func onFixEndButton(_ sender: UIButton) {
        self.presenter?.onFixEnd()
    }
    
    var presenter: TimeSheetPresenterKmp?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.dataSource = self
        tableView.delegate = self
        
        presenter = TimeSheetPresenterKmp(timeSheetView:self, timeSheetRepository:TimeSheetRepositoryImplSwift())
        presenter?.onCreateView()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func addAll(list: [DateModel]) {
        self.datesList.append(contentsOf: list)
        tableView.reloadData()
    }
    
    func clear() {
        self.datesList.removeAll()
        tableView.reloadData()
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

extension ViewController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return datesList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "proto") as? DateViewCell else {
            return DateViewCell()
        }
        if (indexPath.row<datesList.count){
            cell.dateLabel.text = datesList[indexPath.row].date
            cell.startTimeLabel.text = datesList[indexPath.row].startTime
            cell.endTimeLabel.text = datesList[indexPath.row].endTime
            cell.durationLabel.text = datesList[indexPath.row].hours
        }
        return cell
    }
}

extension ViewController: UITableViewDelegate{
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
    }
    
}

class DateViewCell: UITableViewCell{
    @IBOutlet weak var dateLabel:UILabel!
    @IBOutlet weak var startTimeLabel:UILabel!
    @IBOutlet weak var endTimeLabel:UILabel!
    @IBOutlet weak var durationLabel:UILabel!


}
