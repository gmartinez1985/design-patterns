
## Sequence Diagram: Reservation Notification Flow

<details>
<summary>Click to expand PlantUML diagram</summary>

```plantuml
@startuml
hide footbox

actor User

box "Domain Layer" #LightBlue
    participant "Reservation" as Reservation
    participant "ReservationCreated (Event)" as ReservationCreated
    participant "Subject" as Publisher <<Enum>>
    participant "Observer" as Observer <<Interface>>
end box

box "Application Layer" #LightGreen
    participant "ReservationCreatedObserver" as ConcreteObserver
    participant "NotificationSender" as Port <<Interface>>
end box

box "Infrastructure Layer" #LightYellow
    participant "EmailNotificationAdapter" as Adapter
end box

User -> Reservation : createReservation()
activate Reservation
Reservation -> ReservationCreated ** : create
Reservation -> Publisher : publish(event)
deactivate Reservation

activate Publisher
Publisher -> Observer : notify(event)
Observer --> ConcreteObserver : (calls implementation)
deactivate Publisher

activate ConcreteObserver
ConcreteObserver -> Port : sendNotification(...)
deactivate ConcreteObserver

activate Port
Port --> Adapter : (calls implementation)
deactivate Port

Adapter ->] : confirmation sent
@enduml

```
</details> 