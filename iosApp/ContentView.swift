import SwiftUI
import DemoShared

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea()
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        GalleryViewControllerKt.GalleryViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
